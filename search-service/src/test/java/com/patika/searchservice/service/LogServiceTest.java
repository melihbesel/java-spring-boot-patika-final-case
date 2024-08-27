package com.patika.searchservice.service;

import com.patika.searchservice.model.document.LogDocument;
import com.patika.searchservice.repository.LogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class LogServiceTest {

    @Mock
    private LogRepository logRepository;

    @InjectMocks
    private LogService logService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void it_should_find_logs_by_message() {
        // Given
        String message = "test log";
        LogDocument log1 = new LogDocument("1", message, "2024-08-27T12:00:00");
        LogDocument log2 = new LogDocument("2", message, "2024-08-27T13:00:00");
        List<LogDocument> expectedLogs = Arrays.asList(log1, log2);

        when(logRepository.findByMessage(message)).thenReturn(expectedLogs);

        // When
        List<LogDocument> actualLogs = logService.findByMessage(message);

        // Then
        assertEquals(expectedLogs, actualLogs);
    }
}
