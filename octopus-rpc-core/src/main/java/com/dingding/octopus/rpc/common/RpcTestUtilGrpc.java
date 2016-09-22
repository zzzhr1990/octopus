package com.dingding.octopus.rpc.common;

import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.0.0)",
    comments = "Source: test_util.proto")
public class RpcTestUtilGrpc {

  private RpcTestUtilGrpc() {}

  public static final String SERVICE_NAME = "core.RpcTestUtil";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<TestUtilProto.ClientTestRequest,
      TestUtilProto.ServerTestResponse> METHOD_NETWORK_TEST =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "core.RpcTestUtil", "NetworkTest"),
          io.grpc.protobuf.ProtoUtils.marshaller(TestUtilProto.ClientTestRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(TestUtilProto.ServerTestResponse.getDefaultInstance()));
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<TestUtilProto.ClientTestRequest,
      TestUtilProto.ServerTestResponse> METHOD_NETWORK_TEST_ASYNC =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "core.RpcTestUtil", "NetworkTestAsync"),
          io.grpc.protobuf.ProtoUtils.marshaller(TestUtilProto.ClientTestRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(TestUtilProto.ServerTestResponse.getDefaultInstance()));

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static RpcTestUtilStub newStub(io.grpc.Channel channel) {
    return new RpcTestUtilStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static RpcTestUtilBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new RpcTestUtilBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary and streaming output calls on the service
   */
  public static RpcTestUtilFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new RpcTestUtilFutureStub(channel);
  }

  /**
   */
  public static abstract class RpcTestUtilImplBase implements io.grpc.BindableService {

    /**
     */
    public void networkTest(TestUtilProto.ClientTestRequest request,
        io.grpc.stub.StreamObserver<TestUtilProto.ServerTestResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_NETWORK_TEST, responseObserver);
    }

    /**
     */
    public void networkTestAsync(TestUtilProto.ClientTestRequest request,
        io.grpc.stub.StreamObserver<TestUtilProto.ServerTestResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_NETWORK_TEST_ASYNC, responseObserver);
    }

    @Override public io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_NETWORK_TEST,
            asyncUnaryCall(
              new MethodHandlers<
                TestUtilProto.ClientTestRequest,
                TestUtilProto.ServerTestResponse>(
                  this, METHODID_NETWORK_TEST)))
          .addMethod(
            METHOD_NETWORK_TEST_ASYNC,
            asyncUnaryCall(
              new MethodHandlers<
                TestUtilProto.ClientTestRequest,
                TestUtilProto.ServerTestResponse>(
                  this, METHODID_NETWORK_TEST_ASYNC)))
          .build();
    }
  }

  /**
   */
  public static final class RpcTestUtilStub extends io.grpc.stub.AbstractStub<RpcTestUtilStub> {
    private RpcTestUtilStub(io.grpc.Channel channel) {
      super(channel);
    }

    private RpcTestUtilStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected RpcTestUtilStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new RpcTestUtilStub(channel, callOptions);
    }

    /**
     */
    public void networkTest(TestUtilProto.ClientTestRequest request,
        io.grpc.stub.StreamObserver<TestUtilProto.ServerTestResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_NETWORK_TEST, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void networkTestAsync(TestUtilProto.ClientTestRequest request,
        io.grpc.stub.StreamObserver<TestUtilProto.ServerTestResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_NETWORK_TEST_ASYNC, getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class RpcTestUtilBlockingStub extends io.grpc.stub.AbstractStub<RpcTestUtilBlockingStub> {
    private RpcTestUtilBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private RpcTestUtilBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected RpcTestUtilBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new RpcTestUtilBlockingStub(channel, callOptions);
    }

    /**
     */
    public TestUtilProto.ServerTestResponse networkTest(TestUtilProto.ClientTestRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_NETWORK_TEST, getCallOptions(), request);
    }

    /**
     */
    public TestUtilProto.ServerTestResponse networkTestAsync(TestUtilProto.ClientTestRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_NETWORK_TEST_ASYNC, getCallOptions(), request);
    }
  }

  /**
   */
  public static final class RpcTestUtilFutureStub extends io.grpc.stub.AbstractStub<RpcTestUtilFutureStub> {
    private RpcTestUtilFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private RpcTestUtilFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected RpcTestUtilFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new RpcTestUtilFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<TestUtilProto.ServerTestResponse> networkTest(
        TestUtilProto.ClientTestRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_NETWORK_TEST, getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<TestUtilProto.ServerTestResponse> networkTestAsync(
        TestUtilProto.ClientTestRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_NETWORK_TEST_ASYNC, getCallOptions()), request);
    }
  }

  private static final int METHODID_NETWORK_TEST = 0;
  private static final int METHODID_NETWORK_TEST_ASYNC = 1;

  private static class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final RpcTestUtilImplBase serviceImpl;
    private final int methodId;

    public MethodHandlers(RpcTestUtilImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_NETWORK_TEST:
          serviceImpl.networkTest((TestUtilProto.ClientTestRequest) request,
              (io.grpc.stub.StreamObserver<TestUtilProto.ServerTestResponse>) responseObserver);
          break;
        case METHODID_NETWORK_TEST_ASYNC:
          serviceImpl.networkTestAsync((TestUtilProto.ClientTestRequest) request,
              (io.grpc.stub.StreamObserver<TestUtilProto.ServerTestResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @Override
    @SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    return new io.grpc.ServiceDescriptor(SERVICE_NAME,
        METHOD_NETWORK_TEST,
        METHOD_NETWORK_TEST_ASYNC);
  }

}
