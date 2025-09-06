package com.example.chatapp.Repo;

import com.example.chatapp.Model.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConversationRepo extends JpaRepository<Conversation, Long> {
}
