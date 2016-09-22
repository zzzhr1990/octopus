package com.dingding.octopus.rpc.support.autoconfigure;

import com.dingding.octopus.rpc.support.GRpcServerRunner;
import com.dingding.octopus.rpc.support.GRpcService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * GRPC CONFIG
 * Created by herui on 16/6/4.
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(GRpcServerProperties.class)
public class GRpcAutoConfiguration {
    @Bean
    @ConditionalOnBean(annotation = GRpcService.class)
    GRpcServerRunner grpcServerRunner(){
        return new GRpcServerRunner();
    }
}