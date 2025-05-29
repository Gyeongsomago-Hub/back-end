package com.gbsw.gbswhub.domain.chat.db;

import com.gbsw.gbswhub.domain.user.db.UserDto;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class ChatDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Post {
        @NotNull
        private Long user_id;
    }

    @Getter
    @Builder
    public static class RoomResponse {
        private Long room_id;
        private UserDto sender;
        private UserDto receiver;
    }

    @Getter
    @Builder
    public static class MessageResponse {
        private Long message_id;
        private UserDto sender;
        private UserDto receiver;
        private LocalDateTime sendTime;
    }
}
