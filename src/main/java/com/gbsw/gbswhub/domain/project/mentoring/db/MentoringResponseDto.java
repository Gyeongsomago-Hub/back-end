package com.gbsw.gbswhub.domain.project.mentoring.db;

import com.gbsw.gbswhub.domain.project.model.Project;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MentoringResponseDto {
    private Long id;
    private String title;
    private String content;
    private String people;
    private Long view_count;
    private List<String> stack;
    private LocalDate openDate;
    private LocalDate closeDate;
    private Project.Status status = Project.Status.RECRUITING;
    private Long categoryId;
    private Long userId;
}
