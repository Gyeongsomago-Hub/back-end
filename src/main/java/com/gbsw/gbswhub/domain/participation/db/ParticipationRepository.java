package com.gbsw.gbswhub.domain.participation.db;

import com.gbsw.gbswhub.domain.club.model.Club;
import com.gbsw.gbswhub.domain.participation.model.Participation;
import com.gbsw.gbswhub.domain.project.model.Project;
import com.gbsw.gbswhub.domain.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParticipationRepository extends JpaRepository<Participation, Long> {
    List<Participation> findByUser(User user);
    List<Participation> findAllByClub_Id(Long clubId);
    List<Participation> findAllByProject_Id(Long projectId);
    boolean existsByUserAndClub(User user, Club club);
    boolean existsByUserAndProject(User user, Project project);
}
