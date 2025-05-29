package com.gbsw.gbswhub.domain.chat.model;

import com.gbsw.gbswhub.domain.user.model.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long message_id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private LocalDateTime sendTime;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room chatRoom;
}
