package com.example.help_center.service;

import com.example.help_center.model.ChatContent;
import com.example.help_center.model.ChatRoom;
import com.example.help_center.repository.ChatRoomRepository;
import com.example.help_center.util.ResponseUtil;
import com.example.help_center.util.TransformToDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ChatRoomService {

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    private ChatContentService chatContentService;

    @Autowired
    private TransformToDTO transformToDTO;

    public ResponseEntity<Object> createChatRoom(HttpServletRequest request, String userName, String content) {
        try{
            UUID userId = UUID.randomUUID();
            Long epochDate = System.currentTimeMillis();
            ChatRoom newChat = new ChatRoom(userId, userName, null, content, epochDate);
            chatRoomRepository.save(newChat); // roomId is generated here

            ChatContent chatContent = new ChatContent(
                    newChat.getRoomId(),
                    newChat.getUserId().toString(),
                    newChat.getAgentId(),
                    newChat.getUserName(),
                    null,
                    newChat.getContent(),
                    epochDate,
                    false
            );

            chatContentService.addMessage(chatContent);

            return ResponseUtil.dataFound("Data Found", newChat, request);
        } catch (Exception e){
            return ResponseUtil.dataNotFound(e.toString(), request);
        }
    }

    public ResponseEntity<Object> getRoomsPage(Pageable pageable, HttpServletRequest request) {
        Page<ChatRoom> page = null;
        List<ChatRoom> list = null;
        try{
            page = chatRoomRepository.findAll(pageable);
            list = page.getContent();
            if(list.isEmpty()){
                return ResponseUtil.dataNotFound("Data not found", request);
            }
        }catch (Exception e){
            return ResponseUtil.dataNotFound("FE001003031",request);
        }

        return transformToDTO.
                transformObject(new HashMap<>(),
                        list, page,null,null,null ,request);
    }

    public ResponseEntity<Object> getRooms(HttpServletRequest request) {
        List<ChatRoom> chatRoom = chatRoomRepository.findAll();
        if (!chatRoom.isEmpty()) {
            return ResponseUtil.dataFound("Data Found", chatRoom, request);
        } else {
            return ResponseUtil.dataNotFound("Data not Found", request);
        }
    }

    public void updateRoom(Long roomId, ChatContent chatContent) {
        ChatRoom chatRoom = chatRoomRepository.findByRoomId(roomId);
        try {
            if (chatRoom.getAgentId() == null) chatRoom.setAgentId(chatContent.getAgentId());
            chatRoom.setContent(chatContent.getContent());
            chatRoomRepository.save(chatRoom);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
