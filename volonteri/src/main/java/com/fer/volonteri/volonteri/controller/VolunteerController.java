package com.fer.volonteri.volonteri.controller;

import com.fer.volonteri.volonteri.dto.ActivityDetailDto;
import com.fer.volonteri.volonteri.dto.UserDto;
import com.fer.volonteri.volonteri.entity.Volunteer;
import com.fer.volonteri.volonteri.service.UserService;
import com.fer.volonteri.volonteri.service.VolunteerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/volunteers")
public class VolunteerController {

    @Autowired
    private UserService userService;

    @GetMapping("/not-in-activity/{activityId}")
    public ResponseEntity<List<UserDto>> getVolunteersNotInActivity(@PathVariable Long activityId) {
        List<UserDto> result = userService.getVolunteersNotInActivity(activityId);
        return ResponseEntity.ok(result);
    }

}
