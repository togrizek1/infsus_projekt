// File: src/test/java/com/fer/volonteri/repository/ActivityStatusRepositoryTest.java
package com.fer.volonteri.volonteri.repository;

import com.fer.volonteri.volonteri.entity.ActivityStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ActivityStatusRepositoryTest {

    @Mock
    private ActivityStatusRepository activityStatusRepository;

    @Test
    void testFindAllWithMock() {
        ActivityStatus status = new ActivityStatus();
        status.setName("Mocked");

        when(activityStatusRepository.findAll()).thenReturn(List.of(status));

        List<ActivityStatus> result = activityStatusRepository.findAll();
        assertEquals(1, result.size());
        assertEquals("Mocked", result.get(0).getName());

        verify(activityStatusRepository).findAll();
    }
}
