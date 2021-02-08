package com.crsitik.atmosphere.event;

import com.crsitik.atmosphere.chat.JacksonEncoderDecoder;
import com.crsitik.atmosphere.chat.TextMessage;
import com.crsitik.atmosphere.common.atmosphere.AtmosphereHandlerAdapter;
import com.crsitik.atmosphere.common.atmosphere.broadcast.UserBroadcaster;
import org.atmosphere.cache.UUIDBroadcasterCache;
import org.atmosphere.client.TrackMessageSizeInterceptor;
import org.atmosphere.config.service.AtmosphereHandlerService;
import org.atmosphere.config.service.Message;
import org.atmosphere.cpr.AtmosphereResourceFactory;
import org.atmosphere.cpr.BroadcasterFactory;
import org.atmosphere.cpr.MetaBroadcaster;
import org.atmosphere.interceptor.AtmosphereResourceLifecycleInterceptor;
import org.atmosphere.interceptor.BroadcastOnPostAtmosphereInterceptor;
import org.atmosphere.interceptor.HeartbeatInterceptor;
import org.atmosphere.interceptor.SuspendTrackerInterceptor;

import javax.inject.Inject;

/**
 * @author cristik
 */
@AtmosphereHandlerService(path = "/websocket/event",
        broadcasterCache = UUIDBroadcasterCache.class,
        broadcaster = UserBroadcaster.class,
        interceptors = {AtmosphereResourceLifecycleInterceptor.class,
                BroadcastOnPostAtmosphereInterceptor.class,
                TrackMessageSizeInterceptor.class,
                HeartbeatInterceptor.class,
                SuspendTrackerInterceptor.class
        })
public class EventService extends AtmosphereHandlerAdapter<TextMessage> {

    @Inject
    private BroadcasterFactory factory;
    @Inject
    private AtmosphereResourceFactory resourceFactory;
    @Inject
    private MetaBroadcaster metaBroadcaster;

    public EventService() {
        super();
    }

    @Override
    public void deliverMessage() {
        System.out.println(111);
    }

    @Message(encoders = JacksonEncoderDecoder.class, decoders = JacksonEncoderDecoder.class)
    public TextMessage onMessage(TextMessage message) {
        System.out.println(message);
        return message;
    }


}