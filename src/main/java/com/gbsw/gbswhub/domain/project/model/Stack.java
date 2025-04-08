package com.gbsw.gbswhub.domain.project.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Stack {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stack_id;

    @Column(nullable = false)
    private String stack_name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;
}
