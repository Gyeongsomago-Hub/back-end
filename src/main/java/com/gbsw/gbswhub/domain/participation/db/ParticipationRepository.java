package com.gbsw.gbswhub.domain.participation.db;

import com.gbsw.gbswhub.domain.participation.model.Participation;
import com.gbsw.gbswhub.domain.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParticipationRepository extends JpaRepository<Participation, Long> {
    List<Participation> findByUser(User user);
}
