package com.gbsw.gbswhub.domain.chat.db;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1626214022412808015L;

    private Long room_id;

    private Long senderId;

    private String content;

    private String roomId;
}

