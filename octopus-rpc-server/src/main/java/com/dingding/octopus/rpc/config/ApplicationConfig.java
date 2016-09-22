package com.dingding.octopus.rpc.config;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Config at top level
 * Created by guna on 16/9/16.
 */
public class ApplicationConfig {
    private static final Map<String,String> registeredService = new ConcurrentHashMap<>();
    public static Map<String,String> getRegisteredService(){
        return registeredService;
    }
    private static String serverName ="unknown";
    private static final String serverId = UUID.randomUUID().toString();
    public static String getServerName() {
        return serverName;
    }
    public static void setServerName(String serverName) {
        ApplicationConfig.serverName = serverName;
    }
    public static String getServerId(){
        return serverId;
    }
}
