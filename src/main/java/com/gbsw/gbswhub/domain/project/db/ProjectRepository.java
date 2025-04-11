package com.gbsw.gbswhub.domain.project.db;

import com.gbsw.gbswhub.domain.project.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    @Modifying
    @Query("UPDATE Project p SET p.view_count = p.view_count + 1 WHERE p.project_id = :id")
    void incrementViewCount(@Param("id") Long id);
}
