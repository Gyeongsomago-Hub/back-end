package com.gbsw.gbswhub.domain.user.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(nullable = false, unique = true)
    private String username; //아이디

    @Column(nullable = false)
    @Size(min = 8, max = 255)
    private String password; // 비번

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String grade;

    @Column(nullable = false)
    private String classNumber; //반

    @Column(nullable = false)
    private String department; //과

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    public enum Role {
        USER,
        ADMIN,
        CLUB_LEADER
    }
}