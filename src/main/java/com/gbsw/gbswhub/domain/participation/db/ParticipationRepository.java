package com.gbsw.gbswhub.domain.participation.db;

import com.gbsw.gbswhub.domain.participation.model.Participation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipationRepository extends JpaRepository<Participation, Long> {
}
