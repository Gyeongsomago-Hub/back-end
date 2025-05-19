package com.gbsw.gbswhub.domain.category.model;

import com.gbsw.gbswhub.domain.project.model.Project;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long category_id;

    @Column(nullable = false, unique = true)
    private String category_name;

    @OneToMany(mappedBy = "category")
    private List<Project> projects;
}
