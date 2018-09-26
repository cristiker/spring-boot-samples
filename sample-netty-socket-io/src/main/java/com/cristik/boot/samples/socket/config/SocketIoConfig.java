package com.cristik.boot.samples.socket.config;

import com.corundumstudio.socketio.*;
import com.corundumstudio.socketio.annotation.SpringAnnotationScanner;
import org.springframework.context.annotation.Bean;

/**
 * Created by cristik
 */

@org.springframework.context.annotation.Configuration
public class SocketIoConfig {

    @Bean
    public SocketIOServer socketIOServer()
    {
        Configuration config = new Configuration();
        config.setPort(9090);
        SocketConfig socketConfig = new SocketConfig();
        config.setSocketConfig(socketConfig);
        config.setWorkerThreads(100);
//        config.setAuthorizationListener(data -> true);
        SocketIOServer server  = new SocketIOServer(config);
        return server;
    }

    @Bean
    public SpringAnnotationScanner springAnnotationScanner(SocketIOServer socketServer) {
        return new SpringAnnotationScanner(socketServer);
    }

}
