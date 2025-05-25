package com.fer.volonteri.volonteri.service;

import com.fer.volonteri.volonteri.dto.UserDto;
import com.fer.volonteri.volonteri.entity.User;
import com.fer.volonteri.volonteri.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<UserDto> getVolunteersNotInActivity(Long activityId) {
        List<User> users = userRepository.findVolunteersNotInActivity(activityId);
        return users.stream()
                .map(u -> new UserDto(u.getId(), u.getName(), u.getSurname()))
                .toList();
    }
}
