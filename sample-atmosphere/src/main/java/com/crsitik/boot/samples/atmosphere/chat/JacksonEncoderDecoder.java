package com.crsitik.boot.samples.atmosphere.chat;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.atmosphere.config.managed.Decoder;
import org.atmosphere.config.managed.Encoder;

import java.io.IOException;

/**
 * @author cristik
 */

public class JacksonEncoderDecoder implements Encoder<TextMessage, String>, Decoder<String, TextMessage> {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public String encode(TextMessage m) {
        try {
            return this.mapper.writeValueAsString(m);
        } catch (IOException ex) {
            throw new IllegalStateException(ex);
        }
    }

    @Override
    public TextMessage decode(String s) {
        try {
            return this.mapper.readValue(s, TextMessage.class);
        } catch (IOException ex) {
            throw new IllegalStateException(ex);
        }
    }

}
