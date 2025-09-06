package com.example.chatapp.Controller;

import com.example.chatapp.Model.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.time.Instant;

@Controller
public class WebSocketController {

    // Listen on /app/chat and broadcast to /topic/messages
    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public Message send(Message message) {
        message.setTimestamp(Instant.now());
        return message;
    }
}
