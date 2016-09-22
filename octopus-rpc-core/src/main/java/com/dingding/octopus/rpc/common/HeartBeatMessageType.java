package com.dingding.octopus.rpc.common;

/**
 * HB
 * Created by herui on 16/6/18.
 */
public class HeartBeatMessageType {
    public static final int CONNECT = 0;
    public static final int CONNECT_ACCEPT = 1;
    public static final int HEART_BEAT = 2;
    public static final int SERVICE_UPDATE_REQ = 5;
    public static final int SERVICE_UPDATE_RESPONSE = 6;

    public static final int CLIENT_CLOSE = 11;
    public static final int SERVER_CLOSE = 12;


}
