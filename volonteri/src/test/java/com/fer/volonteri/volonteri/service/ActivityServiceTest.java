// File: src/test/java/com/fer/volonteri/service/ActivityServiceTest.java
package com.fer.volonteri.volonteri.service;

import com.fer.volonteri.volonteri.entity.*;
import com.fer.volonteri.volonteri.repository.*;
import com.fer.volonteri.volonteri.dto.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.sql.Time;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ActivityServiceTest {

    @Mock private ActivityRepository activityRepository;
    @Mock private ParticipationRepository participationRepository;
    @Mock private UserRepository userRepository;
    @Mock private LocationRepository locationRepository;

    @InjectMocks private ActivityService activityService;

    private Activity activity;

    @BeforeEach
    void setUp() {
        activity = new Activity();
        activity.setId(1L);
        activity.setName("Test Activity");
        activity.setDate(Date.valueOf("2025-05-01"));
        activity.setTime(Time.valueOf("10:00:00"));

        Organization org = new Organization();
        org.setId(1L); org.setName("Org"); org.setEmail("org@mail.com");
        activity.setOrganization(org);
    }
    @Test
    void $1() {
        System.out.println("Running test: $1"); {
        when(activityRepository.findAll()).thenReturn(List.of(activity));
        List<Activity> result = activityService.getAllActivities();
        assertEquals(1, result.size());
    }
    } 

    @Test
    void testGetAllActivities() {
        when(activityRepository.findAll()).thenReturn(List.of(activity));
        List<Activity> result = activityService.getAllActivities();
        assertEquals(1, result.size());
    }

    @Test
    void testGetActivitySummaries() {
        when(activityRepository.findAll()).thenReturn(List.of(activity));
        List<ActivitySummaryDto> result = activityService.getActivitySummaries();
        assertEquals("Test Activity", result.get(0).getActivityName());
    }

    @Test
    void testGetActivityDetailById() {
        when(activityRepository.findById(1L)).thenReturn(Optional.of(activity));
        when(participationRepository.findByActivityId(1L)).thenReturn(List.of());
        ActivityDetailDto result = activityService.getActivityDetailById(1L);
        assertEquals(1L, result.getId());
    }

    @Test
    void testUpdateActivity() {
        ActivityDetailDto dto = new ActivityDetailDto(1L, "Updated", new ActivityStatus(), "2025-05-20", "14:00:00", null, null, List.of());
        when(activityRepository.findById(1L)).thenReturn(Optional.of(activity));
        activityService.updateActivity(1L, dto);
        verify(activityRepository).save(any(Activity.class));
    }

    @Test
    void testAddVolunteerToActivity() {
        User user = mock(User.class);
        when(activityRepository.findById(1L)).thenReturn(Optional.of(activity));
        when(userRepository.findById(2L)).thenReturn(Optional.of(user));
        when(participationRepository.existsByActivityIdAndUserId(1L, 2L)).thenReturn(false);
        activityService.addVolunteerToActivity(1L, 2L);
        verify(participationRepository).save(any(Participation.class));
    }

    @Test
    void testRemoveVolunteerFromActivity() {
        Participation p = new Participation();
        when(participationRepository.findByActivityIdAndUserId(1L, 2L)).thenReturn(Optional.of(p));
        activityService.removeVolunteerFromActivity(1L, 2L);
        verify(participationRepository).delete(p);
    }

    @Test
    void testUpdateLocationForActivity() {
        LocationDto dto = mock(LocationDto.class);
        when(dto.getCity()).thenReturn("Zagreb");
        when(dto.getCountry()).thenReturn("HR");
        when(dto.getAddress()).thenReturn("Ilica 1");
        Location location = new Location();
        activity.setLocation(location);

        when(activityRepository.findById(1L)).thenReturn(Optional.of(activity));
        when(locationRepository.save(any(Location.class))).thenReturn(location);

        activityService.updateLocationForActivity(1L, dto);
        verify(activityRepository).save(activity);
    }
}
