package com.gbsw.gbswhub.domain.chat.service;

import com.gbsw.gbswhub.domain.chat.model.Message;
import com.gbsw.gbswhub.domain.chat.model.Room;
import com.gbsw.gbswhub.domain.chat.db.MessageDto;
import com.gbsw.gbswhub.domain.chat.model.PublishMessage;
import com.gbsw.gbswhub.domain.chat.repository.MessageRepository;
import com.gbsw.gbswhub.domain.chat.repository.RoomRepository;
import com.gbsw.gbswhub.domain.global.Error.ErrorCode;
import com.gbsw.gbswhub.domain.global.Exception.BusinessException;
import com.gbsw.gbswhub.domain.user.model.User;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.gbsw.gbswhub.domain.global.util.UserValidator.validateUser;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {

    private final MessageRepository messageRepository;
    private final RoomRepository roomRepository;
    private final RoomService roomService;
    private final ChannelTopic topic;

    @Resource(name = "chatRedisTemplate")
    private final RedisTemplate<String, Message> redisTemplate;

    private static final String MESSAGE_KEY = "messageCacheRoom";


    public void CachedMessage(MessageDto dto, Long roomId, User user) {
        validateUser(user);
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ROOM_NOT_FOUND));

        Message message = Message.builder()
                .content(dto.getContent())
                .sender(user)
                .chatRoom(room)
                .sendTime(LocalDateTime.now())
                .build();
        String cacheKey = MESSAGE_KEY + roomId;

        redisTemplate.opsForList().rightPush(cacheKey, message);
    }

    @Scheduled(cron = "0 0 0/1 * * *")
    @Transactional
    public void saveMessages() {
        List<Long> roomList = redisTemplate.keys(MESSAGE_KEY+"*").stream()
                .map(key -> Long.parseLong(key.substring(MESSAGE_KEY.length())))
                .toList();
        for(Long id: roomList) {
            String cacheKey = MESSAGE_KEY + id;
            try {
                List<Message> messages = redisTemplate.opsForList().range(cacheKey, 0, -1);
                if(messages != null && !messages.isEmpty()) {
                    messageRepository.saveAll(messages);
                    redisTemplate.opsForList().trim(cacheKey, messages.size(), -1);
                }
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
    }

    public Page<Message> findMessages(Long roomId, int page, int size) {
        String cacheKey = MESSAGE_KEY + roomId;
        long start = (long) (page - 1) * size;
        long end = start + size - 1;

        List<Message> cachedMessages = redisTemplate.opsForList().range(cacheKey, start, end);

        Room room = roomService.findRoom(roomId);
        List<Message> dbMessages = new ArrayList<>();
        if (cachedMessages.size() < size) {
            int dbPage = page - cachedMessages.size() / size;
            Pageable pageable = PageRequest.of(dbPage, size - cachedMessages.size());
            dbMessages = messageRepository.findAllByChatRoomOrderBySendTimeDesc(room, pageable).getContent();
        }

        List<Message> allMessages = new ArrayList<>();
        allMessages.addAll(cachedMessages);
        allMessages.addAll(dbMessages);
        allMessages.sort(Comparator.comparing(Message::getSendTime));

        int totalElements = allMessages.size();
        int startIndex = (page - 1) * size;
        int endIndex = Math.min(startIndex + size, totalElements);
        List<Message> pageMessages = allMessages.subList(startIndex, endIndex);

        return new PageImpl<>(pageMessages, PageRequest.of(page, size), totalElements);
    }

    public void handleMessage(MessageDto messageDto, Long roomId, User user) {
        PublishMessage publishMessage = new PublishMessage(
                messageDto.getRoom_id(),
                messageDto.getSenderId(),
                messageDto.getContent(),
                LocalDateTime.now()
        );

        // Redis 전송
        redisTemplate.convertAndSend(topic.getTopic(), publishMessage);

        // 메시지 캐싱
        CachedMessage(messageDto, roomId, user);
    }
}
