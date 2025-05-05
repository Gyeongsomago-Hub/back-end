package com.gbsw.gbswhub.domain.participation.model;

import com.gbsw.gbswhub.domain.club.model.Club;
import com.gbsw.gbswhub.domain.project.model.Project;
import com.gbsw.gbswhub.domain.user.model.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Participation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long part_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private Club club;

    @Column
    private String position;

    @Column
    private String introduce;

    @Column
    private String name;

    @Column
    private String grade;
    @Column
    private String classNo;

    @Column
    private String studentNo;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime created_at;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Type type;

    public enum Type {
        PROJECT,
        MENTORING,
        CLUB
    }

    public enum Status {
        REQUESTED,
        APPROVED,
        REJECTED,
    }
}
