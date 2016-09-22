package com.dingding.octopus.rpc.client;

import lombok.Data;

/**
 * Channel Config
 * Created by guna on 16/9/17.
 */
@Data
public class ChannelConfig {
    private String address;
    private String name;
    private String id;
    private int port;
    private long lastPingSuccessTime;
    private long lastPingSuccessSeq;
    private long reconnectTime;
    private int retryCount;
    private boolean connecting;
    private boolean ssl;
}
