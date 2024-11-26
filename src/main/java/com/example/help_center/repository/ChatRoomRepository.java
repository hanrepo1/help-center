package com.example.help_center.repository;

import com.example.help_center.model.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    Optional<List<ChatRoom>> findByAgentId(Long id);

    ChatRoom findByRoomId(Long roomId);
}