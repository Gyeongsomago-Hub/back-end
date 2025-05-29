package com.gbsw.gbswhub.domain.chat.service;

import com.gbsw.gbswhub.domain.chat.model.Room;
import com.gbsw.gbswhub.domain.chat.redis.RedisSubscriber;
import com.gbsw.gbswhub.domain.chat.repository.RoomRepository;
import com.gbsw.gbswhub.domain.global.Error.ErrorCode;
import com.gbsw.gbswhub.domain.global.Exception.BusinessException;
import com.gbsw.gbswhub.domain.user.model.User;
import com.gbsw.gbswhub.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.gbsw.gbswhub.domain.global.util.UserValidator.validateUser;

@Service
@Slf4j
@RequiredArgsConstructor
public class RoomService {

    private final Map<String, ChannelTopic> topics = new ConcurrentHashMap<>();
    private final RedisMessageListenerContainer redisMessageListener;
    private final RedisSubscriber redisSubscriber;
    private final UserService userService;
    private final RoomRepository roomRepository;

    public Map<String, Object> createRoom(long receiverId, User sender) {
        validateUser(sender);

        User receiver = userService.getUserById(receiverId);
        validateUser(sender);

        Room room = roomRepository.findBySenderAndReceiver(sender, receiver)
                .orElseGet(() -> {
                    Room newRoom = Room.builder()
                            .sender(sender)
                            .receiver(receiver)
                            .build();
                    return roomRepository.save(newRoom);
                });

        String roomId = String.valueOf(room.getRoom_id());

        // Redis 토픽 등록
        topics.computeIfAbsent(roomId, id -> {
            ChannelTopic topic = new ChannelTopic(id);
            redisMessageListener.addMessageListener((MessageListener) redisSubscriber, topic);
            return topic;
        });

        Map<String, Object> response = new HashMap<>();
        response.put("message", "채팅방이 생성되었습니다.");
        response.put("roomId", roomId);
        response.put("receiver", receiver.getName());
        return response;
    }

    public Room findRoom(long roomId) {
        Room room = findExistRoom(roomId);

        return room;
    }

    public Room findExistRoom(Long roomId) {
        return roomRepository.findById(roomId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ROOM_NOT_FOUND));
    }
}
