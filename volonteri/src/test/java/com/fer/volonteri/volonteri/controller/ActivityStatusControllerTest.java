// File: src/test/java/com/fer/volonteri/controller/ActivityStatusControllerTest.java
package com.fer.volonteri.volonteri.controller;

import com.fer.volonteri.volonteri.repository.ActivityStatusRepository;
import com.fer.volonteri.volonteri.entity.ActivityStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fer.volonteri.volonteri.config.SecurityConfig;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(ActivityStatusController.class)
@Import(SecurityConfig.class)

class ActivityStatusControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ActivityStatusRepository activityStatusRepository;

    @Test
    void getStatuses_shouldReturn200() throws Exception {
        ActivityStatus status = new ActivityStatus();
        status.setId(1L);
        status.setName("Pending");

        when(activityStatusRepository.findAll()).thenReturn(List.of(status));

        mockMvc.perform(get("/api/activity-status")
                .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$[0].name").value("Pending"));
    }
}