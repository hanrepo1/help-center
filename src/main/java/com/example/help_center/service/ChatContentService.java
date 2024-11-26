package com.example.help_center.service;

import com.example.help_center.model.ChatContent;
import com.example.help_center.repository.ChatContentRepository;
import com.example.help_center.repository.UserRepository;
import com.example.help_center.util.ResponseUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatContentService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChatContentRepository chatContentRepository;

    public void addMessage(ChatContent message) {
        chatContentRepository.save(message);
    }

    public ResponseEntity<Object> getAllMessages(HttpServletRequest request) {
        List<ChatContent> message = chatContentRepository.findAll();
        if (!message.isEmpty()) {
            return ResponseUtil.dataFound("Data Found",message, request);
        } else {
            return ResponseUtil.dataNotFound("Data not found", request);
        }
    }

    public ResponseEntity<Object> getMessageOnRoom(HttpServletRequest request, Long roomId) {
        List<ChatContent> message = chatContentRepository.findByRoomId(roomId);
        if (!message.isEmpty()) {
            return ResponseUtil.dataFound("Data Found",message, request);
        } else {
            return ResponseUtil.dataNotFound("Data not found", request);
        }
    }

}