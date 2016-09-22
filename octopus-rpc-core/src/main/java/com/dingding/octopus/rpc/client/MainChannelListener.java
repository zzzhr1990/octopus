package com.dingding.octopus.rpc.client;

/**
 *
 * Created by guna on 16/9/20.
 */
public interface MainChannelListener {
    void onMainChannelInit();
    void onOneChannelDisconnect();
    void onOneChannelConnect();
}