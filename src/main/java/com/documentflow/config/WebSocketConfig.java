package com.documentflow.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    // Broker = message dispatcher/router
    // receive messages and send to subscribers

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config){

        config.enableSimpleBroker("/topic"); //    server → clients

        config.setApplicationDestinationPrefixes("/app");  //    client → server (for controllers)

    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry){

        registry.addEndpoint("/ws")  //    ws://localhost:8080/ws
                .setAllowedOrigins("*")
                .withSockJS();
    }
}