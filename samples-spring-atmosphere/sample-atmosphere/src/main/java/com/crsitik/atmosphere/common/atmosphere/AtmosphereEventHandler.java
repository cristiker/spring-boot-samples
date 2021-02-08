package com.crsitik.atmosphere.common.atmosphere;

import org.atmosphere.cpr.AtmosphereResourceEvent;

/**
 * @author cristik
 */
public interface AtmosphereEventHandler<T> {

    default void onReady() {

    }

    default void onResume(AtmosphereResourceEvent event) {

    }

    default void onDisconnect(AtmosphereResourceEvent event) {

    }

    default void onTimeout(AtmosphereResourceEvent event) {

    }

}