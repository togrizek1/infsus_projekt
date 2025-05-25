package com.fer.volonteri.volonteri.service;

import com.fer.volonteri.volonteri.entity.Volunteer;
import com.fer.volonteri.volonteri.repository.VolunteerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VolunteerService {

    @Autowired
    private VolunteerRepository volunteerRepository;

    public List<Volunteer> getAllVolunteer() {
        return volunteerRepository.findAll();
    }

    public Volunteer saveVolunteer(Volunteer volunteer) {
        return volunteerRepository.save(volunteer);
    }

}