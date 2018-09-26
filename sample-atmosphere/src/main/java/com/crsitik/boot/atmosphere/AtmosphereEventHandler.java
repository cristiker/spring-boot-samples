package com.crsitik.boot.atmosphere;

import org.atmosphere.cpr.AtmosphereResourceEvent;

/**
 * Created by cristik
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
