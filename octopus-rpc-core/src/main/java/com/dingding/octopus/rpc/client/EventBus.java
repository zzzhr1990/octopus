package com.dingding.octopus.rpc.client;

import java.util.ArrayList;
import java.util.List;

/**
 * EC
 * Created by guna on 16/9/19.
 */
public class EventBus {

    private static List<MainChannelListener> listeners = new ArrayList<>();

    static void oneChannelConnected(){
        listeners.forEach((v)->{
            try {
                v.onOneChannelConnect();
            }catch (Exception ignore){

            }
        });
    }

    static void oneChannelDisconnected(){
        listeners.forEach((v)->{
            try {
                v.onOneChannelDisconnect();
            }catch (Exception ignore){

            }
        });
    }

    public void addListener(MainChannelListener listener){
        listeners.add(listener);
    }
}
