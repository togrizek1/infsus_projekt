package com.fer.volonteri.volonteri.service;

import com.fer.volonteri.volonteri.dto.ActivityDetailDto;
import com.fer.volonteri.volonteri.dto.ActivitySummaryDto;
import com.fer.volonteri.volonteri.dto.LocationDto;
import com.fer.volonteri.volonteri.entity.*;
import com.fer.volonteri.volonteri.repository.ActivityRepository;
import com.fer.volonteri.volonteri.repository.LocationRepository;
import com.fer.volonteri.volonteri.repository.ParticipationRepository;
import com.fer.volonteri.volonteri.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.Time;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ActivityService {

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private ParticipationRepository participationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LocationRepository locationRepository;

    public List<Activity> getAllActivities() {
        return activityRepository.findAll();
    }

    public List<ActivitySummaryDto> getActivitySummaries() {
        List<Activity> activities = activityRepository.findAll();

        return activities.stream()
                .sorted(Comparator.comparing(Activity::getDate))
                .map(activity -> new ActivitySummaryDto(
                        activity.getId(),
                        activity.getName(),
                        activity.getOrganization() != null ? activity.getOrganization().getName() : "N/A"
                ))
                .collect(Collectors.toList());
    }

    public ActivityDetailDto getActivityDetailById(Long id) {
        Optional<Activity> activityOpt = activityRepository.findById(id);
        if (activityOpt.isPresent()) {
            Activity activity = activityOpt.get();

            ActivityDetailDto.OrganizationDto orgDto = new ActivityDetailDto.OrganizationDto(
                    activity.getOrganization().getId(),
                    activity.getOrganization().getName(),
                    activity.getOrganization().getEmail()
            );



            List<Participation> participations = participationRepository.findByActivityId(id);
            List<ActivityDetailDto.UserDto> volunteerDtos = participations.stream()
                    .map(p -> {
                        var user = p.getUser();
                        return new ActivityDetailDto.UserDto(user.getId(), user.getName(), user.getSurname(), user.getEmail());
                    })
                    .collect(Collectors.toList());

            return new ActivityDetailDto(
                    activity.getId(),
                    activity.getName(),
                    activity.getStatus(),
                    activity.getDate().toString(),
                    activity.getTime().toString(),
                    orgDto,
                    activity.getLocation(),
                    volunteerDtos
            );
        }
        return null;
    }

    public void updateActivity(Long id, ActivityDetailDto dto) {
        Activity activity = activityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Activity not found"));

        activity.setName(dto.getName());
        activity.setStatus(dto.getStatus());
        activity.setDate(Date.valueOf(dto.getDate()));
        activity.setTime(Time.valueOf(dto.getTime()));

        activityRepository.save(activity);
    }

    @Transactional
    public void addVolunteerToActivity(Long activityId, Long userId) {
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new RuntimeException("Activity not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        boolean exists = participationRepository.existsByActivityIdAndUserId(activityId, userId);
        if (exists) {
            throw new RuntimeException("Volunteer already added to this activity");
        }

        Participation participation = new Participation();
        participation.setActivity(activity);
        participation.setUser(user);

        participationRepository.save(participation);
    }

    @Transactional
    public void removeVolunteerFromActivity(Long activityId, Long userId) {
        Participation participation = participationRepository.findByActivityIdAndUserId(activityId, userId)
                .orElseThrow(() -> new RuntimeException("Participation not found"));

        participationRepository.delete(participation);
    }

    public void updateLocationForActivity(Long activityId, LocationDto dto) {
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new RuntimeException("Activity not found"));

        Location location = activity.getLocation();
        if (location == null) {
            location = new Location();
        }

        location.setCity(dto.getCity());
        location.setCountry(dto.getCountry());
        location.setAddress(dto.getAddress());

        Location savedLocation = locationRepository.save(location);
        activity.setLocation(savedLocation);
        activityRepository.save(activity);
    }

}
