package com.gbsw.gbswhub.domain.club.repository;

import com.gbsw.gbswhub.domain.club.model.Club;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ClubRepository extends JpaRepository<Club, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT c FROM Club c WHERE c.id = :id")
    Optional<Club> findByIdForUpdate(@Param("id") Long id);

}
