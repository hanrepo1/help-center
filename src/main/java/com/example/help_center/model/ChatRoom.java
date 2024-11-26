package com.example.help_center.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomId;

    private UUID userId;
    private String userName;
    private Long agentId;
    private String content;
    private Long dateCreated;

    // onQueue should be calculated based on agentId
    @Transient // Mark as transient if you don't want it to be persisted
    private boolean onQueue;

    public ChatRoom() {
        // Initialize onQueue based on agentId
        this.onQueue = (this.agentId == null);
    }

    public ChatRoom(UUID userId, String userName, Long agentId, String content, Long dateCreated) {
        this.userId = userId;
        this.userName = userName;
        this.agentId = agentId;
        this.content = content;
        this.dateCreated = dateCreated;
        this.onQueue = agentId == null;
    }
}
