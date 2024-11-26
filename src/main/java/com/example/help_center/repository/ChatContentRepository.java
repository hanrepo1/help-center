package com.example.help_center.repository;

import com.example.help_center.model.ChatContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatContentRepository extends JpaRepository<ChatContent, Long> {


    List<ChatContent> findByRoomId(Long id);
}
