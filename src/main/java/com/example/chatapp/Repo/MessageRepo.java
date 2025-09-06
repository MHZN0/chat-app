package com.example.chatapp.Repo;

import com.example.chatapp.Model.Conversation;
import com.example.chatapp.Model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepo extends JpaRepository<Message, Long> {
    List<Message> findByConversationOrderByTimestampAsc(Conversation conversation);
}
