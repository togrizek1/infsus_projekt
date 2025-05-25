package com.fer.volonteri.volonteri.controller;

import com.fer.volonteri.volonteri.entity.ActivityStatus;
import com.fer.volonteri.volonteri.repository.ActivityStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/activity-status")
public class ActivityStatusController {

    @Autowired
    private ActivityStatusRepository activityStatusRepository;

    @GetMapping
    public List<ActivityStatus> getAllStatuses() {
        return activityStatusRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<ActivityStatus> createStatus(@RequestBody ActivityStatus status) {
        if (status.getName() == null || status.getName().trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        status.setId(null);
        ActivityStatus saved = activityStatusRepository.save(status);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ActivityStatus> updateStatus(@PathVariable Long id, @RequestBody ActivityStatus updatedStatus) {
        Optional<ActivityStatus> optional = activityStatusRepository.findById(id);
        if (optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        ActivityStatus existing = optional.get();
        existing.setName(updatedStatus.getName());
        activityStatusRepository.save(existing);
        return ResponseEntity.ok(existing);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStatus(@PathVariable Long id) {
        if (!activityStatusRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        activityStatusRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
    
}
