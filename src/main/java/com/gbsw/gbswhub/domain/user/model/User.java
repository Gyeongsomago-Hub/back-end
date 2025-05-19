package com.gbsw.gbswhub.domain.user.model;

import com.gbsw.gbswhub.domain.ai.model.Ai;
import com.gbsw.gbswhub.domain.club.model.Club;
import com.gbsw.gbswhub.domain.participation.model.Participation;
import com.gbsw.gbswhub.domain.project.model.Project;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;

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

    @Column
    private String studentNumber;

    @Column(nullable = false)
    private String department; //과

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Participation> participation= new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Project> project = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Club> club = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ai> Ai = new ArrayList<>();

    public enum Role {
        USER,
        ADMIN,
        CLUB_LEADER,
        MENTOR
    }
}