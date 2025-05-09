package com.gbsw.gbswhub.domain.project.model;

import com.gbsw.gbswhub.domain.category.model.Category;
import com.gbsw.gbswhub.domain.user.model.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    private Long view_count;

    @Column(nullable = false)
    private String people;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime created_at;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Stack> stacks = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Project.Status status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Project.Type type;

    @Column(nullable = false)
    private LocalDate openDate;

    @Column(nullable = false)
    private LocalDate closeDate;


    public enum Status {
        RECRUITING,  //모집 중
        COMPLETED //모집 완료
    }

    public enum Type {
        PROJECT,
        MENTORING
    }

    public boolean isOwner(User user) {
        return this.user != null && this.user.getUser_id().equals(user.getUser_id());
    }
}

