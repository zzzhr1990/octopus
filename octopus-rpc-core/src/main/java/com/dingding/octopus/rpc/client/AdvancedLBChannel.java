package com.dingding.octopus.rpc.client;

import com.dingding.octopus.rpc.common.CoreUtilProto;
import com.dingding.octopus.rpc.common.DetectUtils;
import com.dingding.octopus.rpc.common.HeartBeatMessageType;
import com.dingding.octopus.rpc.common.RpcServerUtilGrpc;
import io.grpc.*;
import io.grpc.stub.StreamObserver;
import io.netty.handler.ssl.SslProvider;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.*;

/**
 * XD
 * Created by guna on 16/9/16.
 */

@Slf4j
public class AdvancedLBChannel extends Channel{


    private final Map<String,List<TrackedChannel>> methodMap= new ConcurrentHashMap<>();
    private final Set<TrackedChannel> channelSet = new CopyOnWriteArraySet<>();
    private final Random rand = new Random();
    private static final TL tl = new TL();

    public AdvancedLBChannel(){
        log.info("Create main LBC Channel...");
        new Thread(()->{
            while (tl.isRunning()){
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException ignore) {
                    return;
                }

                long current = System.currentTimeMillis();
                channelSet.forEach((channel)->{
                    ChannelConfig config = channel.getConfig();
                    if(current - config.getLastPingSuccessTime() > 30000){
                        closeChannel(channel);
                    }
                    if(!config.isConnecting()){
                        StreamObserver<CoreUtilProto.ClientInformation> observer = channel.getObserver();
                        if(observer != null){
                            if (!tl.isRunning()){
                                return;
                            }
                            observer.onNext(CoreUtilProto.ClientInformation.newBuilder()
                                    .setId(config.getId()).setSeq(config.getLastPingSuccessSeq() + 1).setName(config.getName())
                                    .setType(HeartBeatMessageType.HEART_BEAT).setTime(current)
                                    .build());
                        }
                    }


                });
            }
        }).start();

    }

    public Map<String, List<TrackedChannel>>  getMethodMap() {
        return methodMap;
    }

    public void addChannel(ChannelConfig config,boolean waitForConnect){
        //Blind and ask for

        CoreUtilProto.ClientInformation clientInformation = CoreUtilProto.ClientInformation.newBuilder().setId(config.getId()).setType(HeartBeatMessageType.CONNECT)
                .setName(config.getName()).setSeq(0).setTime(System.currentTimeMillis()).build();
        TrackedChannel channel = new TrackedChannel(config);
        config.setConnecting(true);
        config.setLastPingSuccessSeq(0);
        config.setLastPingSuccessTime(System.currentTimeMillis());
        channelSet.add(channel);
        CompletableFuture<Channel> completableFuture = new CompletableFuture<>();
        StreamObserver<CoreUtilProto.ClientInformation> observer = RpcServerUtilGrpc.newStub(channel).heartBeat(new StreamObserver<CoreUtilProto.ServerInformation>() {
            @Override
            public void onNext(CoreUtilProto.ServerInformation value) {
                switch (value.getType()){
                    case HeartBeatMessageType.CONNECT_ACCEPT:
                        Map<String, String> servicesMap = value.getServicesMap();
                        log.info("Connection accepted,server {} ({}:{})", config.getName(),config.getAddress(),config.getPort() );
                        if(servicesMap != null){
                            //Loop
                            servicesMap.forEach((k,v) -> {
                                //v:Method Name
                                List<TrackedChannel> channels = methodMap.get(v);
                                if(channels == null){
                                    channels = new ArrayList<>();
                                    methodMap.put(v,channels);
                                }
                                channels.add(channel);
                                config.setConnecting(false);
                                config.setRetryCount(0);
                            });
                        }
                        if (waitForConnect){
                            completableFuture.complete(channel);
                        }else {
                            EventBus.oneChannelConnected();
                        }
                        break;

                    case HeartBeatMessageType.HEART_BEAT:
                        onHeartBeat(value,channel);
                        break;

                    default:
                        log.warn("Unknown message type {}",value.getType());

                }
            }

            @Override
            public void onError(Throwable t) {
                log.info("Remote Server Channel error..",t);
            }

            @Override
            public void onCompleted() {
                closeChannel(channel);
            }
        });
        channel.setObserver(observer);
        observer.onNext(clientInformation);
        if (waitForConnect){
            try {
                completableFuture.get(2000, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                log.error("InterruptedException when add services.",e);
            } catch (ExecutionException e) {
                log.error("ExecutionException when add services.",e);
            } catch (TimeoutException e) {
                log.error("Wait Channel {} timed out",channel.toString(),e);
            }
        }

    }

    @Override
    public <RequestT, ResponseT> ClientCall<RequestT, ResponseT> newCall(MethodDescriptor<RequestT, ResponseT> methodDescriptor, CallOptions callOptions) {
        List<TrackedChannel> channels = methodMap.get(methodDescriptor.getFullMethodName());
        if(channels == null || channels.size() < 1){
            log.error("No Server can handle request {}",methodDescriptor.getFullMethodName());
            throw new RuntimeException("No Server can handle request " + methodDescriptor.getFullMethodName());
        }
        return channels.get(rand.nextInt(channels.size())).newCall(methodDescriptor, callOptions);
    }

    @Override
    public String authority() {
        return null;
    }


    private void onHeartBeat(CoreUtilProto.ServerInformation serverInformation,TrackedChannel channel){
        ChannelConfig channelConfig = channel.getConfig();
        channelConfig.setLastPingSuccessSeq(serverInformation.getSeq());
        channelConfig.setLastPingSuccessTime(System.currentTimeMillis());
        channelConfig.setConnecting(false);
        channelConfig.setRetryCount(0);

    }

    private void closeChannel(TrackedChannel channel){
        log.info("Remote Server Channel closed..");
        EventBus.oneChannelDisconnected();
        synchronized (methodMap){
            methodMap.forEach((k,v)-> v.remove(channel));

            methodMap.forEach((k,v)->{
                if(v.size() < 1){
                    methodMap.remove(k);
                }
            });
            channelSet.remove(channel);
            log.info("Remote Server Channel left..{}",channelSet.size());
            new Thread(()->{
                try {
                    Thread.sleep(2000);
                }catch (Exception ignore){

                }
                if(tl.isRunning()) {
                    retryConnection(channel.getConfig());
                }
            }).start();

        }

        //TODO : Retry??

    }

    public void close(){
        tl.setRunning(false);
        log.info("Sending close info to server..");
        channelSet.forEach((v)->{
            StreamObserver<CoreUtilProto.ClientInformation> observer = v.getObserver();
            if(observer != null){
                try {
                    observer.onCompleted();
                }catch (Exception ignore){

                }
            }
        });
        try {
            log.info("Wait 2000ms to ensure closed.");
            Thread.sleep(2000);
        }catch (Exception ignore){

        }

    }

    private void retryConnection(ChannelConfig config){
        config.setConnecting(true);
        config.setRetryCount(0);
        config.setReconnectTime(System.currentTimeMillis());
        log.info("Retrying Connection {} , on {}:{}",config.getName(),config.getAddress(),config.getPort());
        addChannel(config,false);
    }
}
@Data
class TL{
    private boolean running = true;
}