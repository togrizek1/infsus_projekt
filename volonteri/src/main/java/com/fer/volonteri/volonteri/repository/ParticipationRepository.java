package com.fer.volonteri.volonteri.repository;

import com.fer.volonteri.volonteri.entity.Participation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ParticipationRepository extends JpaRepository<Participation, Long> {
    List<Participation> findByActivityId(Long activityId);
    boolean existsByActivityIdAndUserId(Long activityId, Long userId);
    Optional<Participation> findByActivityIdAndUserId(Long activityId, Long userId);
}
