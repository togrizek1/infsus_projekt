package com.fer.volonteri.volonteri;

import com.fer.volonteri.volonteri.entity.ActivityStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestH2Repository extends JpaRepository<ActivityStatus, Long> {
}
