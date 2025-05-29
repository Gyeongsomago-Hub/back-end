package com.gbsw.gbswhub.domain.chat.repository;

import com.gbsw.gbswhub.domain.chat.model.Room;
import com.gbsw.gbswhub.domain.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
   Optional<Room> findBySenderAndReceiver(User sender, User receiver);
}
