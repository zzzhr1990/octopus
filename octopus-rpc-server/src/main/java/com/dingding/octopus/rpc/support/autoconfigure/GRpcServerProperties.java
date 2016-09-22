package com.dingding.octopus.rpc.support.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Config
 * Created by herui on 16/6/4.
 */
@ConfigurationProperties("grpc")
@Data
public class GRpcServerProperties {
    /**
     * gRPC server port
     */
    private int port = 6565;

    private boolean ssl = false;

    private String serverName = "gRpc Server";

}