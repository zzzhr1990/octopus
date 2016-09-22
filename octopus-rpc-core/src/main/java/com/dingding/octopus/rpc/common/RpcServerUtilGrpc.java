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
    comments = "Source: core_util.proto")
public class RpcServerUtilGrpc {

  private RpcServerUtilGrpc() {}

  public static final String SERVICE_NAME = "core.RpcServerUtil";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<CoreUtilProto.ClientInformation,
      CoreUtilProto.ServerInformation> METHOD_HEART_BEAT =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING,
          generateFullMethodName(
              "core.RpcServerUtil", "HeartBeat"),
          io.grpc.protobuf.ProtoUtils.marshaller(CoreUtilProto.ClientInformation.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(CoreUtilProto.ServerInformation.getDefaultInstance()));

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static RpcServerUtilStub newStub(io.grpc.Channel channel) {
    return new RpcServerUtilStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static RpcServerUtilBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new RpcServerUtilBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary and streaming output calls on the service
   */
  public static RpcServerUtilFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new RpcServerUtilFutureStub(channel);
  }

  /**
   */
  public static abstract class RpcServerUtilImplBase implements io.grpc.BindableService {

    /**
     */
    public io.grpc.stub.StreamObserver<CoreUtilProto.ClientInformation> heartBeat(
        io.grpc.stub.StreamObserver<CoreUtilProto.ServerInformation> responseObserver) {
      return asyncUnimplementedStreamingCall(METHOD_HEART_BEAT, responseObserver);
    }

    @Override public io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_HEART_BEAT,
            asyncBidiStreamingCall(
              new MethodHandlers<
                CoreUtilProto.ClientInformation,
                CoreUtilProto.ServerInformation>(
                  this, METHODID_HEART_BEAT)))
          .build();
    }
  }

  /**
   */
  public static final class RpcServerUtilStub extends io.grpc.stub.AbstractStub<RpcServerUtilStub> {
    private RpcServerUtilStub(io.grpc.Channel channel) {
      super(channel);
    }

    private RpcServerUtilStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected RpcServerUtilStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new RpcServerUtilStub(channel, callOptions);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<CoreUtilProto.ClientInformation> heartBeat(
        io.grpc.stub.StreamObserver<CoreUtilProto.ServerInformation> responseObserver) {
      return asyncBidiStreamingCall(
          getChannel().newCall(METHOD_HEART_BEAT, getCallOptions()), responseObserver);
    }
  }

  /**
   */
  public static final class RpcServerUtilBlockingStub extends io.grpc.stub.AbstractStub<RpcServerUtilBlockingStub> {
    private RpcServerUtilBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private RpcServerUtilBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected RpcServerUtilBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new RpcServerUtilBlockingStub(channel, callOptions);
    }
  }

  /**
   */
  public static final class RpcServerUtilFutureStub extends io.grpc.stub.AbstractStub<RpcServerUtilFutureStub> {
    private RpcServerUtilFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private RpcServerUtilFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected RpcServerUtilFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new RpcServerUtilFutureStub(channel, callOptions);
    }
  }

  private static final int METHODID_HEART_BEAT = 0;

  private static class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final RpcServerUtilImplBase serviceImpl;
    private final int methodId;

    public MethodHandlers(RpcServerUtilImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }

    @Override
    @SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_HEART_BEAT:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.heartBeat(
              (io.grpc.stub.StreamObserver<CoreUtilProto.ServerInformation>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    return new io.grpc.ServiceDescriptor(SERVICE_NAME,
        METHOD_HEART_BEAT);
  }

}
