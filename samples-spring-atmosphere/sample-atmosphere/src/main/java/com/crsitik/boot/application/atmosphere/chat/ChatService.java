/*
 * Copyright 2012-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.crsitik.boot.application.atmosphere.chat;

import org.atmosphere.config.service.Disconnect;
import org.atmosphere.config.service.ManagedService;
import org.atmosphere.config.service.Message;
import org.atmosphere.config.service.Ready;
import org.atmosphere.cpr.AtmosphereResource;
import org.atmosphere.cpr.AtmosphereResourceEvent;
import org.atmosphere.util.ExcludeSessionBroadcaster;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@ManagedService(path = "/websocket/chat", broadcaster = ExcludeSessionBroadcaster.class)
public class ChatService {

    private final Logger logger = LoggerFactory.getLogger(ChatService.class);

    @Ready
    public void onReady(AtmosphereResource resource) {
        this.logger.info("Connected", resource.uuid());
    }

    @Disconnect
    public void onDisconnect(AtmosphereResourceEvent event) {
        this.logger.info("Client {} disconnected [{}]", event.getResource().uuid(),
                (event.isCancelled() ? "cancelled" : "closed"));
    }

    @Message(encoders = JacksonEncoderDecoder.class, decoders = JacksonEncoderDecoder.class)
    public TextMessage onMessage(TextMessage message) throws IOException {
        this.logger.info("Author {} sent message {}", message.getAuthor(),
                message.getMessage());
        return message;
    }

}
