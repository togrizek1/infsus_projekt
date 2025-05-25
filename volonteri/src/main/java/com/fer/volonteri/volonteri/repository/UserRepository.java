package com.fer.volonteri.volonteri.repository;

import com.fer.volonteri.volonteri.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u JOIN Volunteer v ON u.id = v.id WHERE u.id NOT IN (SELECT p.user.id FROM Participation p WHERE p.activity.id = :activityId)")
    List<User> findVolunteersNotInActivity(@Param("activityId") Long activityId);

}
