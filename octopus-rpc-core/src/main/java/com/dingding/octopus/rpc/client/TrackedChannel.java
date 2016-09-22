package com.dingding.octopus.rpc.client;

import com.dingding.octopus.rpc.common.CoreUtilProto;
import com.dingding.octopus.rpc.common.DetectUtils;
import io.grpc.*;
import io.grpc.stub.StreamObserver;
import io.netty.handler.ssl.SslProvider;
import lombok.extern.slf4j.Slf4j;

/**
 * HoHo
 * Created by guna on 16/9/17.
 */
@Slf4j
public class TrackedChannel extends Channel{

    private Channel innerChannel;

    public StreamObserver<CoreUtilProto.ClientInformation> getObserver() {
        return observer;
    }

    public void setObserver(StreamObserver<CoreUtilProto.ClientInformation> observer) {
        this.observer = observer;
    }

    private StreamObserver<CoreUtilProto.ClientInformation> observer;

    public ChannelConfig getConfig() {
        return config;
    }

    private ChannelConfig config;
    public TrackedChannel(ChannelConfig channelConfig){
        ManagedChannelBuilder builder = ManagedChannelBuilder.forAddress(channelConfig.getAddress(), channelConfig.getPort());
        SslProvider sslProvider = DetectUtils.selectApplicationProtocolConfig();
        if(channelConfig.isSsl()){
            if (sslProvider == null) {
                log.warn("You configured SSL but sslProvider not found");
                builder.usePlaintext(true);
            }else {
                builder.usePlaintext(false);
            }
        }else{
            builder.usePlaintext(true);
        }
        innerChannel = builder.build();
        config = channelConfig;
    }
    @Override
    public <RequestT, ResponseT> ClientCall<RequestT, ResponseT> newCall(MethodDescriptor<RequestT, ResponseT> methodDescriptor, CallOptions callOptions) {
        return innerChannel.newCall(methodDescriptor, callOptions);
    }

    @Override
    public String authority() {
        return innerChannel.authority();
    }

}
