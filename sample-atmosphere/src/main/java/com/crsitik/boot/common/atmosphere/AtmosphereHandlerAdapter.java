package com.crsitik.boot.common.atmosphere;


import org.atmosphere.config.managed.Decoder;
import org.atmosphere.config.managed.Encoder;
import org.atmosphere.config.managed.Invoker;
import org.atmosphere.config.managed.ManagedAtmosphereHandler;
import org.atmosphere.config.service.DeliverTo;
import org.atmosphere.config.service.Message;
import org.atmosphere.cpr.*;
import org.atmosphere.handler.AnnotatedProxy;
import org.atmosphere.util.IOUtils;
import org.atmosphere.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.atmosphere.cpr.ApplicationConfig.PROPERTY_USE_STREAM;
import static org.atmosphere.util.IOUtils.isBodyEmpty;
import static org.atmosphere.util.IOUtils.readEntirely;


/**
 * @author cristik
 */
public abstract class AtmosphereHandlerAdapter<T> implements AnnotatedProxy, AtmosphereEventHandler<T>, AtmosphereMethodHandler, AtmosphereHandler {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Inject
    private AtmosphereConfig config;

    protected List<AtmosphereHandlerAdapter.MethodInfo> onRuntimeMethod;
    final Map<Method, List<Encoder<?, ?>>> encoders = new HashMap();
    final Map<Method, List<Decoder<?, ?>>> decoders = new HashMap();

    public AtmosphereHandlerAdapter() {
        this.onRuntimeMethod = populateMessage(this, Message.class);
        populateEncoders();
        populateDecoders();
    }

    @Override
    public void onRequest(AtmosphereResource resource) throws IOException {
        AtmosphereRequest request = resource.getRequest();
        String method = request.getMethod();
        boolean polling = Utils.pollableTransport(resource.transport());
        boolean webSocketMessage = Utils.webSocketMessage(resource);

        if (!webSocketMessage && !polling) {
            resource.addEventListener(new AtmosphereResourceEventListenerAdapter.OnSuspend() {
                @Override
                public void onSuspend(AtmosphereResourceEvent event) {
                    processReady(event.getResource());
                    resource.removeEventListener(this);
                }
            });

            resource.addEventListener(new AtmosphereResourceEventListenerAdapter.OnResume() {
                @Override
                public void onResume(AtmosphereResourceEvent event) {
                    AtmosphereHandlerAdapter.this.onResume(event);
                    resource.removeEventListener(this);
                }
            });

            resource.addEventListener(new AtmosphereResourceEventListenerAdapter.OnClose() {
                @Override
                public void onClose(AtmosphereResourceEvent event) {
                    AtmosphereHandlerAdapter.this.onDisconnect(event);
                }
            });
        }

        if (method.equalsIgnoreCase("get")) {
            onGet(resource);
        } else if (method.equalsIgnoreCase("post")) {
            Object body;
            body = readEntirely(resource);
            if (body != null && String.class.isAssignableFrom(body.getClass())) {
                resource.getRequest().body((String) body);
            } else if (body != null) {
                resource.getRequest().body((byte[]) body);
            }
            onPost(resource);
            deliverMessage();


        } else if (method.equalsIgnoreCase("delete")) {
            onDelete(resource);
        } else if (method.equalsIgnoreCase("put")) {
            onPut(resource);
        }
    }

    public abstract void deliverMessage();

    @Override
    public void onStateChange(AtmosphereResourceEvent event) throws IOException {
        Object msg = event.getMessage();
        AtmosphereResourceImpl r = AtmosphereResourceImpl.class.cast(event.getResource());

        if (r == null) {
            return;
        }
        Boolean resumeOnBroadcast = r.resumeOnBroadcast();
        if (!resumeOnBroadcast) {
            Object o = r.getRequest(false).getAttribute(ApplicationConfig.RESUME_ON_BROADCAST);
            if (o != null && Boolean.class.isAssignableFrom(o.getClass())) {
                resumeOnBroadcast = Boolean.class.cast(o);
            }
        }

        // Disable resume so cached message can be send in one chunk.
        if (resumeOnBroadcast) {
            r.resumeOnBroadcast(false);
            r.getRequest(false).setAttribute(ApplicationConfig.RESUME_ON_BROADCAST, false);
        }

        if (event.isCancelled() || event.isClosedByClient()) {
            onDisconnect(event);
        } else if (event.isResumedOnTimeout() || event.isResuming()) {
            onTimeout(event);
        } else {
            Object o;
            if (msg != null) {
                if (ManagedAtmosphereHandler.Managed.class.isAssignableFrom(msg.getClass())) {
                    Object newMsg = ManagedAtmosphereHandler.Managed.class.cast(msg).object();
                    event.setMessage(newMsg);
                    // TODO: This could be problematic with String + method
                    if (r.getBroadcaster().getBroadcasterConfig().hasFilters()) {
                        for (AtmosphereHandlerAdapter.MethodInfo m : onRuntimeMethod) {
                            o = Invoker.encode(encoders.get(m.method), newMsg);
                            if (o != null) {
                                event.setMessage(o);
                                break;
                            }
                        }
                    }
                } else {
                    logger.trace("BroadcasterFactory has been used, this may produce recursion if encoder/decoder match the broadcasted message");
                    final AtmosphereHandlerAdapter.MethodInfo.EncoderObject e = message(r, msg);
                    o = e == null ? null : e.encodedObject;
                    if (o != null) {
                        event.setMessage(o);
                    }
                }
            }
            onStateChangeMethod(event);
        }

        if (resumeOnBroadcast && r.isSuspended()) {
            r.resume();
        }
    }

    @Override
    public void destroy() {

    }

    @Override
    public AnnotatedProxy configure(AtmosphereConfig config, Object c) {
        return null;
    }

    @Override
    public Object target() {
        return null;
    }

    @Override
    public boolean pathParams() {
        return false;
    }

    protected void processReady(AtmosphereResource r) {
        logger.info("process resource", r);
    }

    private void onStateChangeMethod(AtmosphereResourceEvent event) throws IOException {
        Object message = event.getMessage();
        AtmosphereResource resource = event.getResource();
        AtmosphereResponse r = resource.getResponse();
        AtmosphereRequest request = resource.getRequest();

        boolean writeAsBytes = IOUtils.isBodyBinary(request);
        if (message == null) {
            logger.trace("Message was null for AtmosphereEvent {}", event);
            return;
        }

        if (resource.getSerializer() != null) {
            try {

                if (message instanceof List) {
                    for (Object s : (List<Object>) message) {
                        resource.getSerializer().write(resource.getResponse().getOutputStream(), s);
                    }
                } else {
                    resource.getSerializer().write(resource.getResponse().getOutputStream(), message);
                }
            } catch (Throwable ex) {
                logger.warn("Serializer exception: message: {}", message, ex);
                throw new IOException(ex);
            }
        } else {
            boolean isUsingStream = true;
            Object o = resource.getRequest().getAttribute(PROPERTY_USE_STREAM);
            if (o != null) {
                isUsingStream = (Boolean) o;
            }

            if (!isUsingStream) {
                try {
                    r.getWriter();
                } catch (IllegalStateException e) {
                    isUsingStream = true;
                }
                if (writeAsBytes) {
                    throw new IllegalStateException("Cannot write bytes using PrintWriter");
                }
            }

            if (message instanceof List) {
                Iterator<Object> i = ((List) message).iterator();
                try {
                    Object s;
                    while (i.hasNext()) {
                        s = i.next();
                        if (String.class.isAssignableFrom(s.getClass())) {
                            if (isUsingStream) {
                                r.getOutputStream().write(s.toString().getBytes(r.getCharacterEncoding()));
                            } else {
                                r.getWriter().write(s.toString());
                            }
                        } else if (byte[].class.isAssignableFrom(s.getClass())) {
                            if (isUsingStream) {
                                r.getOutputStream().write((byte[]) s);
                            } else {
                                r.getWriter().write(s.toString());
                            }
                        } else {
                            if (isUsingStream) {
                                r.getOutputStream().write(s.toString().getBytes(r.getCharacterEncoding()));
                            } else {
                                r.getWriter().write(s.toString());
                            }
                        }
                        i.remove();
                    }
                } catch (IOException ex) {
                    event.setMessage(new ArrayList<String>().addAll((List) message));
                    throw ex;
                }

                if (isUsingStream) {
                    r.getOutputStream().flush();
                } else {
                    r.getWriter().flush();
                }
            } else {
                if (isUsingStream) {
                    r.getOutputStream().write(writeAsBytes ? (byte[]) message : message.toString().getBytes(r.getCharacterEncoding()));
                    r.getOutputStream().flush();
                } else {
                    r.getWriter().write(message.toString());
                    r.getWriter().flush();
                }
            }
        }
        postStateChange(event);
    }

    private AtmosphereHandlerAdapter.MethodInfo.EncoderObject message(AtmosphereResource resource, Object o) {
        AtmosphereRequest request = AtmosphereResourceImpl.class.cast(resource).getRequest(false);
        try {
            for (AtmosphereHandlerAdapter.MethodInfo m : onRuntimeMethod) {
                if (m.useReader) {
                    o = request.getReader();
                } else if (m.useStream) {
                    o = request.getInputStream();
                } else if (o == null) {
                    o = readEntirely(resource);
                    if (isBodyEmpty(o)) {
                        logger.warn("{} received an empty body", request);
                        return null;
                    }
                }

                Object decoded = Invoker.decode(decoders.get(m.method), o);
                if (decoded == null) {
                    decoded = o;
                }
                Object objectToEncode = null;

                if (m.method.getParameterTypes().length > 2) {
                    logger.warn("Injection of more than 2 parameters not supported {}", m);
                }

                if (m.method.getParameterTypes().length == 2) {
                    objectToEncode = Invoker.invokeMethod(m.method, this, resource, decoded);
                } else {
                    objectToEncode = Invoker.invokeMethod(m.method, this, decoded);
                }

                if (objectToEncode != null) {
                    return m.encode(encoders, objectToEncode);
                }
            }
        } catch (Throwable t) {
            logger.error("", t);
        }
        return null;
    }

    protected final void postStateChange(AtmosphereResourceEvent event) {
        if (event.isCancelled() || event.isResuming()) {
            return;
        }

        AtmosphereResourceImpl r = AtmosphereResourceImpl.class.cast(event.getResource());
        // Between event.isCancelled and resource, the connection has been remotly closed.
        if (r == null) {
            logger.trace("Event {} returned a null AtmosphereResource", event);
            return;
        }
        Boolean resumeOnBroadcast = r.resumeOnBroadcast();
        if (!resumeOnBroadcast) {
            // For legacy reason, check the attribute as well
            Object o = r.getRequest(false).getAttribute(ApplicationConfig.RESUME_ON_BROADCAST);
            if (o != null && Boolean.class.isAssignableFrom(o.getClass())) {
                resumeOnBroadcast = Boolean.class.cast(o);
            }
        }

        if (resumeOnBroadcast != null && resumeOnBroadcast) {
            r.resume();
        }
    }

    protected List<AtmosphereHandlerAdapter.MethodInfo> populateMessage(Object c, Class<? extends Annotation> annotation) {
        List<AtmosphereHandlerAdapter.MethodInfo> methods = new ArrayList();
        for (Method m : c.getClass().getMethods()) {
            if (m.isAnnotationPresent(annotation)) {
                methods.add(new AtmosphereHandlerAdapter.MethodInfo(m));
            }
        }
        return methods;
    }

    private void populateEncoders() {
        for (AtmosphereHandlerAdapter.MethodInfo m : onRuntimeMethod) {
            List<Encoder<?, ?>> l = new CopyOnWriteArrayList();
            for (Class<? extends Encoder> s : m.method.getAnnotation(Message.class).encoders()) {
                try {
                    l.add(config.framework().newClassInstance(Encoder.class, s));
                } catch (Exception e) {
                    logger.error("Unable to load encoder {}", s);
                }
            }
            encoders.put(m.method, l);
        }
    }

    private void populateDecoders() {
        for (AtmosphereHandlerAdapter.MethodInfo m : onRuntimeMethod) {
            List<Decoder<?, ?>> l = new CopyOnWriteArrayList<Decoder<?, ?>>();
            for (Class<? extends Decoder> s : m.method.getAnnotation(Message.class).decoders()) {
                try {
                    l.add(config.framework().newClassInstance(Decoder.class, s));
                } catch (Exception e) {
                    logger.error("Unable to load encoder {}", s);
                }
            }
            decoders.put(m.method, l);
        }
    }



    public final static class MethodInfo {
        final Method method;
        final DeliverTo.DELIVER_TO deliverTo;
        boolean useStream;
        boolean useReader;

        public MethodInfo(Method method) {
            this.method = method;

            if (method.isAnnotationPresent(DeliverTo.class)) {
                this.deliverTo = method.getAnnotation(DeliverTo.class).value();
            } else {
                this.deliverTo = DeliverTo.DELIVER_TO.BROADCASTER;
            }
        }

        AtmosphereHandlerAdapter.MethodInfo.EncoderObject encode(final Map<Method, List<Encoder<?, ?>>> encoders, final Object objectToEncode) {
            return new AtmosphereHandlerAdapter.MethodInfo.EncoderObject(encoders, objectToEncode);
        }

        class EncoderObject {
            final Object encodedObject;
            final AtmosphereHandlerAdapter.MethodInfo methodInfo;

            public EncoderObject(final Map<Method, List<Encoder<?, ?>>> encoders, final Object objectToEncode) {
                encodedObject = Invoker.encode(encoders.get(method), objectToEncode);
                methodInfo = AtmosphereHandlerAdapter.MethodInfo.this;
            }
        }
    }
}


