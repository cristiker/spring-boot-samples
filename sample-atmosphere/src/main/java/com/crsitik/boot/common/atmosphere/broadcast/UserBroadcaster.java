package com.crsitik.boot.common.atmosphere.broadcast;

import org.atmosphere.cpr.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Future;

/**
 * @author cristik
 */
public class UserBroadcaster extends DefaultBroadcaster {

    private static final String DESTROYED = "This Broadcaster has been destroyed and cannot be used {} by invoking {}";

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Inject
    private AtmosphereResourceFactory resourceFactory;

    @Override
    public Future<Object> broadcast(Object msg) {

        if (destroyed.get()) {
            logger.debug(DESTROYED, getID(), "broadcast(T msg)");
            return futureDone(msg);
        }

        start();
        Object newMsg = filter(msg);
        if (newMsg == null) {
            logger.debug("Broadcast Interrupted {}", msg);
            return futureDone(msg);
        }

        int callee = resources.isEmpty() ? 1 : resources.size();

        BroadcasterFuture<Object> f = new BroadcasterFuture(newMsg, callee);
        Set<AtmosphereResource> resourceSetToDeliver = new HashSet<>();
        for (AtmosphereResource resource : resources) {
            if (resource.uuid() != null) {
                resourceSetToDeliver.add(resource);
            }
        }
        Deliver deliver = new Deliver(msg, resourceSetToDeliver, f, msg);
        dispatchMessages(deliver);
        return f;
    }
}
