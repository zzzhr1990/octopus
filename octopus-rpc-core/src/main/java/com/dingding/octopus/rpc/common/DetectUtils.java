package com.dingding.octopus.rpc.common;

import io.netty.handler.ssl.ApplicationProtocolConfig;
import io.netty.handler.ssl.OpenSsl;
import io.netty.handler.ssl.SslProvider;

/**
 * Created by herui on 16/6/19.
 */
public class DetectUtils {
    private static SslProvider defaultSslProvider() {
        return OpenSsl.isAvailable() ? SslProvider.OPENSSL : SslProvider.JDK;
    }

    public static SslProvider selectApplicationProtocolConfig(){
        return selectApplicationProtocolConfig(defaultSslProvider());
    }

    public static SslProvider selectApplicationProtocolConfig(SslProvider provider) {
        switch (provider) {
            case JDK: {
                if (isJettyAlpnConfigured()) {
                    return provider;
                }
                if (isJettyNpnConfigured()) {
                    return provider;
                }
                return null;
                //throw new IllegalArgumentException("Jetty ALPN/NPN has not been properly configured.");
            }
            case OPENSSL: {
                if (!OpenSsl.isAvailable()) {
                    //log.warn("OpenSSL is not installed on the system.");
                    return null;
                }

                if (OpenSsl.isAlpnSupported()) {
                    return provider;
                } else {
                    return provider;
                }
            }
            default:
                throw new IllegalArgumentException("Unsupported provider: " + provider);
        }
    }

    /**
     * Indicates whether or not the Jetty ALPN jar is installed in the boot classloader.
     */
    static boolean isJettyAlpnConfigured() {
        try {
            Class.forName("org.eclipse.jetty.alpn.ALPN", true, null);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    /**
     * Indicates whether or not the Jetty NPN jar is installed in the boot classloader.
     */
    static boolean isJettyNpnConfigured() {
        try {
            Class.forName("org.eclipse.jetty.npn.NextProtoNego", true, null);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }
}

