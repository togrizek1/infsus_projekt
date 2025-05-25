package com.fer.volonteri.volonteri.controller;

import com.fer.volonteri.volonteri.dto.ActivityDetailDto;
import com.fer.volonteri.volonteri.dto.ActivitySummaryDto;
import com.fer.volonteri.volonteri.dto.LocationDto;
import com.fer.volonteri.volonteri.entity.Activity;
import com.fer.volonteri.volonteri.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/activity")
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @GetMapping
    public ResponseEntity<List<Activity>> getAllActivities(){
        List<Activity> allActivities = activityService.getAllActivities();
        System.out.println(allActivities);
        return ResponseEntity.ok(allActivities);
    }

    @GetMapping("/summary")
    public List<ActivitySummaryDto> getActivitySummaries() {
        return activityService.getActivitySummaries();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActivityDetailDto> getActivityDetailById(@PathVariable Long id) {
        ActivityDetailDto dto = activityService.getActivityDetailById(id);
        if (dto != null) {
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateActivity(@PathVariable Long id, @RequestBody ActivityDetailDto dto) {
        activityService.updateActivity(id, dto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{activityId}/volunteers/{userId}")
    public ResponseEntity<Void> addVolunteerToActivity(@PathVariable Long activityId, @PathVariable Long userId) {
        activityService.addVolunteerToActivity(activityId, userId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{activityId}/volunteers/{userId}")
    public ResponseEntity<Void> removeVolunteerFromActivity(@PathVariable Long activityId, @PathVariable Long userId) {
        activityService.removeVolunteerFromActivity(activityId, userId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{activityId}/location")
    public ResponseEntity<Void> updateLocation(@PathVariable Long activityId, @RequestBody LocationDto dto) {
        activityService.updateLocationForActivity(activityId, dto);
        return ResponseEntity.ok().build();
    }

}
