package com.dingding.octopus.rpc.support;

import com.dingding.octopus.rpc.common.DetectUtils;
import com.dingding.octopus.rpc.config.ApplicationConfig;
import com.dingding.octopus.rpc.service.DisposableService;
import com.dingding.octopus.rpc.service.ServerUtilService;
import com.dingding.octopus.rpc.support.autoconfigure.GRpcServerProperties;
import io.grpc.*;
import io.grpc.netty.NettyServerBuilder;
import io.netty.handler.ssl.SslProvider;
import io.netty.handler.ssl.util.SelfSignedCertificate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.Callable;

/**
 *  Hosts embedded gRPC server.
 */
@Slf4j
//@Service
public class GRpcServerRunner implements CommandLineRunner,DisposableBean {

    /**
     * Name of static function of gRPC service-outer class that creates {@link ServerServiceDefinition}.
     */
    final private  static String bindServiceMethodName = "bindService";


    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private GRpcServerProperties gRpcServerProperties;


    private Server server;

    public GRpcServerRunner(){
        log.info("gRPC Runner init ...");
    }

    @Override
    @SuppressWarnings("unchecked")
    public void run(String... args) throws Exception {

        //final ServerBuilder<?> serverBuilder = ServerBuilder.forPort(gRpcServerProperties.getPort());
        final NettyServerBuilder serverBuilder = NettyServerBuilder.forPort(gRpcServerProperties.getPort());

        log.info("Starting gRPC Server using {} ...",serverBuilder.getClass().getName());

        //TLS?

        if(gRpcServerProperties.isSsl()){
            log.info("Starting SSL Server...");
            //Check if can enable TLS...

            SslProvider sslProvider = DetectUtils.selectApplicationProtocolConfig();
            if(sslProvider == null){
                log.warn("Failed to load ssl provider");
            }
            else {
                SelfSignedCertificate ssc = new SelfSignedCertificate();
                serverBuilder.useTransportSecurity(ssc.certificate(),ssc.privateKey());
                log.info("Use SelfSignedCertificate to start TLS...");
            }
        }

        // find and register all GRpcService-enabled beans
        for(Object grpcService : applicationContext.getBeansWithAnnotation(GRpcService.class).values()) {
            /*
            final Class<?> grpcServiceOuterClass = AnnotationUtils.findAnnotation(grpcService.getClass(), GRpcService.class).grpcServiceOuterClass();

            // find 'bindService' method on outer class.
            final Optional<Method> bindServiceMethod = Arrays.asList(ReflectionUtils.getAllDeclaredMethods(grpcServiceOuterClass)).stream().filter(
                    method ->  bindServiceMethodName.equals(method.getName()) && 1 == method.getParameterCount() && method.getParameterTypes()[0].isAssignableFrom(grpcService.getClass())
            ).findFirst();

            // register service
            // May be there's two or more grpc services?
            if (bindServiceMethod.isPresent()) {
                ServerServiceDefinition serviceDefinition = (ServerServiceDefinition) bindServiceMethod.get().invoke(null, grpcService);
                serverBuilder.addService(serviceDefinition);
                log.info("'{}' service has been registered.", serviceDefinition.getName());
            } else {
                throw new IllegalArgumentException(String.format("Failed to find '%s' method on class %s.\r\n" +
                                "Please make sure you've provided correct 'grpcServiceOuterClass' attribute for '%s' annotation.\r\n" +
                                "It should be the protoc-generated outer class of your service."
                        , bindServiceMethodName,grpcServiceOuterClass.getName(), GRpcService.class.getName()));
            }
            */
            //Finding Services...

            //grpcService instanceof
            Class<?> aClass = grpcService.getClass();
            GRpcService annotation = aClass.getAnnotation(GRpcService.class);
            // Load Inceptors
            List<ServerInterceptor> interceptors = new ArrayList<>();
            if(annotation != null){
                Class<? extends ServerInterceptor>[] classes = annotation.serverInterceptors();
                if(classes.length > 0){
                    for (Class<? extends ServerInterceptor> c:classes){
                        if(c.isInterface()){
                            continue;
                        }
                        try {
                            ServerInterceptor serverInterceptor = c.newInstance();
                            interceptors.add(serverInterceptor);
                            log.info("ServerInterceptor {} defined for {}.",c.getName(),aClass.getName());
                        }catch (Exception e){
                            //serverBuilder.addService(serverServiceDefinition);
                            log.warn("Initialize interceptor {} failed." , c.getName(),e);
                        }
                    }
                }
            }
            //
            if(grpcService instanceof ServerServiceDefinition){
                ServerServiceDefinition serverServiceDefinition = (ServerServiceDefinition) grpcService;
                serverBuilder.addService(interceptors.size() > 0 ? ServerInterceptors
                        .intercept(serverServiceDefinition,interceptors) : serverServiceDefinition);
                log.info("Add ServerServiceDefinition {}",aClass.getName());
            }else if (grpcService instanceof BindableService ){
                BindableService bindableService = (BindableService) grpcService;
                serverBuilder.addService(interceptors.size() > 0 ? ServerInterceptors.intercept(bindableService.bindService(),interceptors) : bindableService.bindService());
                log.info("Add BindableService {}",aClass.getName());
            }else{
                log.warn("Cannot recognise service {}",aClass.getName());
            }
        }
        server = serverBuilder.build().start();

        ApplicationConfig.setServerName(gRpcServerProperties.getServerName());
        log.info("gRPC Server {} started, listening on port {}.",gRpcServerProperties.getServerName(), gRpcServerProperties.getPort());
        boolean checkSuccess = false;
        Map<String, String> registeredService = ApplicationConfig.getRegisteredService();
        for (Field f:server.getClass().getDeclaredFields()) {
            //log.info("{}",f.getName());
            if(f.getName().equals("registry"))
            {
                try {
                    f.setAccessible(true);
                    Object o = f.get(server);
                    if(o!= null){
                        for (Field n:o.getClass().getDeclaredFields()){
                            if(n.getName().equals("methods")){
                                n.setAccessible(true);
                                Object methods = n.get(o);
                                //log.info("Methods {}",methods.getClass().getName());
                                if(methods instanceof Map){
                                    //ImmutableMap<String, ServerMethodDefinition<?, ?>>
                                    Map<String, ServerMethodDefinition<?, ?>> map = (Map<String, ServerMethodDefinition<?, ?>>)methods;
                                    map.forEach((k,v) ->{
                                        registeredService.put(k,v.getMethodDescriptor().getFullMethodName());
                                    });
                                    checkSuccess = true;
                                }
                                break;
                            }
                        }
                    }
                }catch (Exception e){
                    log.error("Init Services failed.  ",e);
                }
                break;
            }
        };
        if(!checkSuccess){
            log.warn("Check Registered Services failed.");
        }
        startDaemonAwaitThread();

    }

    private void startDaemonAwaitThread() {
        Thread awaitThread = new Thread(()-> {
            try {
                GRpcServerRunner.this.server.awaitTermination();
            } catch (InterruptedException e) {
                log.error("gRPC server stopped.",e);
            }
        });
        awaitThread.setDaemon(false);
        awaitThread.start();
    }
    @Override
    public void destroy() throws Exception {
        Map<String, DisposableService> beansOfType = applicationContext.getBeansOfType(DisposableService.class);
        if(beansOfType != null){
            log.info("Sending close to {} channel ...",beansOfType.size());
            beansOfType.forEach((k,v)->v.beforeClose());
        }
        ServerUtilService bean = applicationContext.getBean(ServerUtilService.class);
        if(bean != null){
            bean.close();
        }
        try {
            log.info("Wait 1000 ms to make sure main util service connection close...");
            Thread.sleep(1000);
        }catch (Exception e) {
            log.info("Shutting down main util service exception.",e);
        }
        log.info("Shutting down gRPC server ...");
        Optional.ofNullable(server).ifPresent(Server::shutdown);
        log.info("gRPC server stopped.");
    }
}