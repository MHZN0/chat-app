package com.example.chatapp.Controller;

import com.example.chatapp.Model.Conversation;
import com.example.chatapp.Model.Message;
import com.example.chatapp.Model.User;
import com.example.chatapp.Repo.ConversationRepo;
import com.example.chatapp.Repo.MessageRepo;
import com.example.chatapp.Repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin(origins = "*")
public class ChatController {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ConversationRepo conversationRepo;

    @Autowired
    private MessageRepo messageRepo;

    // Get all conversations for a user
    @GetMapping("/conversations/{userId}")
    public ResponseEntity<?> getUserConversations(@PathVariable Long userId) {
        Optional<User> userOpt = userRepo.findById(userId);
        return userOpt.map(user -> ResponseEntity.ok(user.getConversations()))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Get messages of a conversation
    @GetMapping("/messages/{conversationId}")
    public ResponseEntity<?> getMessages(@PathVariable Long conversationId) {
        Optional<Conversation> convoOpt = conversationRepo.findById(conversationId);
        return convoOpt.map(conversation ->
                ResponseEntity.ok(messageRepo.findByConversationOrderByTimestampAsc(conversation))
        ).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Send message (updated to accept JSON object)
    @PostMapping("/messages/{conversationId}/send/{userId}")
    public ResponseEntity<?> sendMessage(@PathVariable Long conversationId,
                                         @PathVariable Long userId,
                                         @RequestBody Map<String, String> body) { // ✅ updated

        Optional<Conversation> convoOpt = conversationRepo.findById(conversationId);
        Optional<User> userOpt = userRepo.findById(userId);

        if (convoOpt.isPresent() && userOpt.isPresent()) {
            String content = body.get("content"); // extract content from JSON
            Message msg = Message.builder()
                    .content(content)
                    .sender(userOpt.get())
                    .conversation(convoOpt.get())
                    .timestamp(Instant.now())
                    .build();
            return ResponseEntity.ok(messageRepo.save(msg));
        }
        return ResponseEntity.badRequest().body("Invalid conversation or user");
    }
}
