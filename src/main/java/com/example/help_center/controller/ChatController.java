package com.example.help_center.controller;

import com.example.help_center.model.ChatContent;
import com.example.help_center.service.ChatContentService;
import com.example.help_center.service.ChatRoomService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/chat")
public class ChatController {

    @Autowired
    private ChatContentService chatContentService;

    @Autowired
    private ChatRoomService chatRoomService;

    @MessageMapping("/send/{roomId}") // Maps messages sent to /app/send
    @SendTo("/topic/{roomId}") // Sends the message to all subscribers of /topic/messages
    public ChatContent sendMessage(
            @DestinationVariable Long roomId,
            String json
    ) throws Exception
    {
        // You can process the message here (e.g., save to DB)
        System.out.println("Received message: {}"+ json);

        ChatContent payload = new Gson().fromJson(String.valueOf(json), ChatContent.class);
        ChatContent chatContent = new ChatContent(
                roomId,
                payload.getUserId(),
                payload.getAgentId(),
                payload.getUserName(),
                payload.getAgentName(),
                payload.getContent(),
                payload.getTimeSent(),
                payload.isAgent()
        );

        chatContentService.addMessage(chatContent);
        chatRoomService.updateRoom(roomId, chatContent);

        return chatContent; // Echo back the message
    }

    @GetMapping("/messages")
    public ResponseEntity<Object> getMessages(HttpServletRequest request) {
        return chatContentService.getAllMessages(request);
    }
    @GetMapping("/messages/{roomId}")
    public ResponseEntity<Object> getMessageOnRoom(HttpServletRequest request, @PathVariable Long roomId) {
        return chatContentService.getMessageOnRoom(request, roomId);
    }

    @PostMapping("/start")
    public ResponseEntity<Object> startChat(
            HttpServletRequest request,
//            @RequestParam Long userId,
            @RequestParam String userName,
            @RequestParam(defaultValue = "content") String content)
    {
        return chatRoomService.createChatRoom(request, userName, content);
    }

    @GetMapping("/getRooms/{page}/{sort}/{sort-by}")
    public ResponseEntity<Object> getRoomsPage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,//page yang ke ?
            @RequestParam(value = "sort", defaultValue = "asc") String sort,//asc desc
            @RequestParam(value = "sort-by", defaultValue = "id") String sortBy,// column Name in java Variable,
            @RequestParam("size") Integer size,
            HttpServletRequest request
    ){
        page = (page==null)?0:page;
        /** function yang bersifat global di paging , untuk memberikan default jika data request tidak mengirim format sort dengan benar asc/desc */
        Pageable pageable;
        if (sort.equalsIgnoreCase("asc")){
            pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        } else {
            pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());
        }

        return chatRoomService.getRoomsPage(pageable, request);
    }

    @GetMapping("/getRooms")
    public ResponseEntity<Object> getRooms(HttpServletRequest request) {
        return chatRoomService.getRooms(request);
    }

//    @GetMapping("/updateRoom/{roomId}")
//    public ResponseEntity<Object> updateRoom(
//            HttpServletRequest request,
//            @PathVariable Long roomId,
//            @RequestBody ChatContent chatContent
//    ) {
//        return chatRoomService.updateRoom(request, roomId, chatContent);
//    }

    @GetMapping("/quickText/create")
    public ResponseEntity<Object> createQuickText(HttpServletRequest request) {
        return null;
    }

    @GetMapping("/quickText/update/{id}")
    public ResponseEntity<Object> updateQuickText(
            HttpServletRequest request,
            @PathVariable long id) {
        return null;
    }

    @GetMapping("/quickText/delete/{id}")
    public ResponseEntity<Object> deleteQuickText(
            HttpServletRequest request,
            @PathVariable long id) {
        return null;
    }

    @GetMapping("/quickText/find/{id}")
    public ResponseEntity<Object> findQuickTextById(
            HttpServletRequest request,
            @PathVariable long id) {
        return null;
    }

    @GetMapping("/quickText/findAll/{page}/{sort}/{sort-by}")
    public ResponseEntity<Object> findAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,//page yang ke ?
            @RequestParam(value = "sort", defaultValue = "asc") String sort,//asc desc
            @RequestParam(value = "sort-by", defaultValue = "id") String sortBy,// column Name in java Variable,
            @RequestParam("size") Integer size,
            HttpServletRequest request
    ){
        page = (page==null)?0:page;
        /** function yang bersifat global di paging , untuk memberikan default jika data request tidak mengirim format sort dengan benar asc/desc */
        Pageable pageable;
        if (sort.equalsIgnoreCase("asc")){
            pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        } else {
            pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());
        }
//        return chatContentService.findAll(pageable,request);
        return null;
    }
}