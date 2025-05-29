package com.gbsw.gbswhub.domain.chat.repository;

import com.gbsw.gbswhub.domain.chat.model.Message;
import com.gbsw.gbswhub.domain.chat.model.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    Page<Message> findAllByChatRoomOrderBySendTimeDesc(Room chatRoom, Pageable pageable);
}
