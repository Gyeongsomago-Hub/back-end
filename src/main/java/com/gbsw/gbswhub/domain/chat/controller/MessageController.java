package com.gbsw.gbswhub.domain.chat.controller;

import com.gbsw.gbswhub.domain.chat.model.Message;
import com.gbsw.gbswhub.domain.chat.db.ChatDto;
import com.gbsw.gbswhub.domain.chat.db.MessageDto;
import com.gbsw.gbswhub.domain.chat.db.MultiResponseDto;
import com.gbsw.gbswhub.domain.chat.db.PageInfoDto;
import com.gbsw.gbswhub.domain.chat.mapper.ChatMapper;
import com.gbsw.gbswhub.domain.chat.service.ChatService;
import com.gbsw.gbswhub.domain.user.model.User;
import com.gbsw.gbswhub.domain.user.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
@Tag(name = "Chat", description = "채팅 관련 API")
public class MessageController {

    private final ChatService chatService;
    private final ChannelTopic topic;
    private final UserService userService;
    private final ChatMapper mapper;

    @Resource(name = "chatRedisTemplate")
    private final RedisTemplate redisTemplate;

    @MessageMapping("/message/{roomId}")
    public void message(@DestinationVariable Long roomId, @Valid @RequestBody MessageDto messageDto , Principal principal) {
        User user = userService.getUser(principal.getName());

        chatService.handleMessage(messageDto, roomId, user);
    }

    @GetMapping("/message/{roomId}")
    public ResponseEntity getMessage(@Positive @PathVariable Long roomId,
                                     @Positive @RequestParam(defaultValue = "1") int page,
                                     @Positive @RequestParam(defaultValue = "10") int size,
                                     Principal principal) {
        User user = userService.getUser(principal.getName());

        Page<Message> messages = chatService.findMessages(roomId, page, size);
        PageInfoDto pageInfoDto = new PageInfoDto(page, size, (int)messages.getTotalElements(), messages.getTotalPages());

        List<Message> messageList = messages.getContent();
        List<ChatDto.MessageResponse> messageResponses = mapper.messagesToMessageResponseDtos(messageList);
        return new ResponseEntity<>(new MultiResponseDto<>(messageResponses, pageInfoDto), HttpStatus.OK);
    }
}
