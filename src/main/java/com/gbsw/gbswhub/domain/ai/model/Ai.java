package com.gbsw.gbswhub.domain.ai.model;

import com.gbsw.gbswhub.domain.user.model.User;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Ai {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ai_id;

    @Column(nullable = false)
    private String question;

    @Column(nullable = false)
    private String qnswer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
