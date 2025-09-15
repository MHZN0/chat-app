package com.example.chatapp.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * WebSocketConfig enables STOMP over WebSocket.
 * This allows real-time chat messages to be sent between clients.
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // Enable a simple in-memory message broker
        registry.enableSimpleBroker("/topic"); // messages sent to /topic will be broadcast
        registry.setApplicationDestinationPrefixes("/app"); // client sends messages to /app
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Endpoint for websocket connection
        registry.addEndpoint("/ws-chat")
                .setAllowedOriginPatterns("http://localhost:3000") // ✅ replaced setAllowedOrigins
                .withSockJS(); // fallback if websocket not supported
    }
}
