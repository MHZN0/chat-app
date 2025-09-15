package com.example.chatapp.Controller;

import com.example.chatapp.dto.MessageDTO;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    /**
     * Handles messages sent to /app/chat
     * Broadcasts them to /topic/messages
     */
    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public MessageDTO sendMessage(MessageDTO message) {
        // Here you can also save messages to DB if needed
        return message;
    }
}
