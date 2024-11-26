package com.example.help_center.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class ChatContent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long roomId;
    private String userId;
    private Long agentId;
    private String userName;
    private String agentName;
    private String content;
    private Long timeSent;

    @JsonProperty("isAgent")
    private boolean isAgent = false;

    public ChatContent(Long roomId,
                       String userId,
                       Long agentId,
                       String userName,
                       String agentName,
                       String content,
                       Long timeSent,
                       boolean isAgent) {
        this.roomId = roomId;
        this.userId = userId;
        this.agentId = agentId;
        this.userName = userName;
        this.agentName = agentName;
        this.content = content;
        this.timeSent = timeSent;
        this.isAgent = isAgent;
    }

    public ChatContent() {

    }
}