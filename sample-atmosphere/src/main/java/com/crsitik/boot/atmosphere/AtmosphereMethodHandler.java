package com.crsitik.boot.atmosphere;

import org.atmosphere.cpr.AtmosphereResource;

/**
 * Created by cristik
 */
public interface AtmosphereMethodHandler {

    default void onGet(AtmosphereResource resource) {

    }

    default void onPost(AtmosphereResource resource) {

    }

    default void onDelete(AtmosphereResource resource) {

    }

    default void onPut(AtmosphereResource resource) {

    }


}
