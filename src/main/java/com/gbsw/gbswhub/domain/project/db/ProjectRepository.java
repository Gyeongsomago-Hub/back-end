package com.gbsw.gbswhub.domain.project.db;

import com.gbsw.gbswhub.domain.club.model.Club;
import com.gbsw.gbswhub.domain.project.model.Project;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    @Modifying
    @Query("UPDATE Project p SET p.view_count = p.view_count + 1 WHERE p.id = :id")
    void incrementViewCount(@Param("id") Long id);

    List<Project> findByType(Project.Type type);
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT p FROM Project p WHERE p.id = :id AND p.type = 'MENTORING' AND p.status = 'RECRUITING'")
    Optional<Project> findByIdForUpdate(@Param("id") Long id);



}