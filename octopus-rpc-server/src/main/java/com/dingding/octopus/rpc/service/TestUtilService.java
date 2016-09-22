package com.dingding.octopus.rpc.service;

import com.dingding.octopus.rpc.common.RpcTestUtilGrpc;
import com.dingding.octopus.rpc.common.TestUtilProto;
import com.dingding.octopus.rpc.config.ApplicationConfig;
import com.dingding.octopus.rpc.support.GRpcService;
import io.grpc.stub.StreamObserver;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * XD
 * Created by guna on 16/9/18.
 */
@GRpcService(grpcServiceOuterClass = TestUtilProto.class)
public class TestUtilService extends RpcTestUtilGrpc.RpcTestUtilImplBase{
    @Override
    public void networkTest(TestUtilProto.ClientTestRequest request, StreamObserver<TestUtilProto.ServerTestResponse> responseObserver) {
        responseObserver.onNext(createResponse(request));
        responseObserver.onCompleted();
    }

    @Override
    public void networkTestAsync(TestUtilProto.ClientTestRequest request, StreamObserver<TestUtilProto.ServerTestResponse> responseObserver) {
        responseObserver.onNext(createResponse(request));
        responseObserver.onCompleted();
    }

    private TestUtilProto.ServerTestResponse createResponse(TestUtilProto.ClientTestRequest request){
        return TestUtilProto.ServerTestResponse.newBuilder()
                .setId(ApplicationConfig.getServerId())
                .setName(ApplicationConfig.getServerName())
                .setTime(System.currentTimeMillis())
                .setLoad(0).setMessage(request.getMessage()).build();
    }

}
