package com.gbsw.gbswhub.domain.ai.model.repository;

import com.gbsw.gbswhub.domain.ai.model.Ai;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AiRepository extends JpaRepository<Ai, Long> {
}
