package com.crsitik.boot.common.atmosphere;

import org.atmosphere.cpr.AtmosphereResource;

/**
 * @author cristik
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
