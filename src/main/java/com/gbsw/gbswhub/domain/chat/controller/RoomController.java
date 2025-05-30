package com.gbsw.gbswhub.domain.chat.controller;

import com.gbsw.gbswhub.domain.chat.db.ChatDto;
import com.gbsw.gbswhub.domain.chat.mapper.ChatMapper;
import com.gbsw.gbswhub.domain.chat.service.RoomService;
import com.gbsw.gbswhub.domain.user.model.User;
import com.gbsw.gbswhub.domain.user.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static com.gbsw.gbswhub.domain.global.util.UserValidator.validateUser;

@RestController
@RequestMapping("/api/room")
@RequiredArgsConstructor
@Tag(name = "Room", description = "채팅방 관련 API")
public class RoomController {

    private final UserService userService;
    private final RoomService roomService;
    private final ChatMapper mapper;

    @PostMapping
    public ResponseEntity<Void> getOrCreateRoom(@Valid @RequestBody ChatDto.Post postDto, User user) {
        validateUser(user);

        User receiver = userService.getUserById(postDto.getUser_id());
        Long roomId = roomService.createRoom(user, receiver);

        URI location = UriComponentsBuilder.newInstance()
                .path("/api/chat/{room-id}")
                .buildAndExpand(roomId)
                .toUri();

        return ResponseEntity.created(location).build();
    }

}
