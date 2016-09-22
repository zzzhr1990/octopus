package com.dingding.octopus.rpc.service;

import com.dingding.octopus.rpc.common.CoreUtilProto;
import com.dingding.octopus.rpc.common.HeartBeatMessageType;
import com.dingding.octopus.rpc.common.RpcServerUtilGrpc;
import com.dingding.octopus.rpc.config.ApplicationConfig;
import com.dingding.octopus.rpc.support.GRpcService;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Comm servers.
 * Created by guna on 16/9/16.
 */
@GRpcService(grpcServiceOuterClass = CoreUtilProto.class)
@Slf4j
public class ServerUtilService extends RpcServerUtilGrpc.RpcServerUtilImplBase{

    private Set<StreamObserver<CoreUtilProto.ServerInformation>> observers = new CopyOnWriteArraySet<>();

    private CoreUtilProto.ServerInformation buildConnectServerInfo(CoreUtilProto.ClientInformation clientInformation){
        return  CoreUtilProto.ServerInformation.newBuilder()
                .setType(HeartBeatMessageType.CONNECT_ACCEPT)
                .setId(ApplicationConfig.getServerId())
                .setLoad(0).setName(ApplicationConfig.getServerName())
                .setSeq(0).setTime(System.currentTimeMillis()).putAllServices(ApplicationConfig.getRegisteredService()
                ).build();
    }

    private CoreUtilProto.ServerInformation buildHeartBeatServerInfo(CoreUtilProto.ClientInformation clientInformation){
        return  CoreUtilProto.ServerInformation.newBuilder()
                .setType(HeartBeatMessageType.HEART_BEAT)
                .setId(ApplicationConfig.getServerId())
                .setLoad(0).setName(ApplicationConfig.getServerName())
                .setSeq(clientInformation.getSeq() + 1).setTime(System.currentTimeMillis()).build();
    }

    @Override
    public StreamObserver<CoreUtilProto.ClientInformation> heartBeat(StreamObserver<CoreUtilProto.ServerInformation> responseObserver) {
        observers.add(responseObserver);
        return new StreamObserver<CoreUtilProto.ClientInformation>() {

            @Override
            public void onNext(CoreUtilProto.ClientInformation value) {
                switch (value.getType()) {
                    case HeartBeatMessageType.CONNECT:
                        log.info("New client connect...");
                        responseObserver.onNext(buildConnectServerInfo(value));
                        break;
                    case HeartBeatMessageType.HEART_BEAT:
                        responseObserver.onNext(buildHeartBeatServerInfo(value));
                        break;
                    default:
                        log.warn("Unknown message type {}", value.getType());
                }

            }

            @Override
            public void onError(Throwable t) {
                log.error("Received Client Error : {}", t);
            }

            @Override
            public void onCompleted() {
                observers.remove(responseObserver);
                log.info("Connection closed from client. {} left.",observers.size());
            }
        };

    }


    //@Override
    public void close() {
        log.info("Sending closing info to client channel {}",observers.size());
        observers.forEach((v)->{
            try{
                v.onCompleted();
            }catch (Exception ignore){

            }
        });
    }
}
