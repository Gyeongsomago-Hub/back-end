package com.gbsw.gbswhub.domain.project.db;

import com.gbsw.gbswhub.domain.project.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
