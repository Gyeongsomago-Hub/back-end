package com.gbsw.gbswhub.domain.chat.mapper;

import com.gbsw.gbswhub.domain.chat.model.Message;
import com.gbsw.gbswhub.domain.chat.model.Room;
import com.gbsw.gbswhub.domain.chat.db.ChatDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ChatMapper {
    ChatDto.RoomResponse chatRoomToRoomResponseDto(Room chatRoom);
    List<ChatDto.RoomResponse> chatRoomListToRoomResponseDtos(List<Room> chatRooms);

    ChatDto.MessageResponse messageToMessageResponseDto(Message message);
    List<ChatDto.MessageResponse> messagesToMessageResponseDtos(List<Message> messages);
}
