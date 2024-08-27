package com.patika.searchservice.service;

import com.patika.searchservice.model.document.ExceptionDocument;
import com.patika.searchservice.repository.ExceptionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class ExceptionServiceTest {

    @Mock
    private ExceptionRepository exceptionRepository;

    @InjectMocks
    private ExceptionService exceptionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void it_should_find_exceptions_by_message() {
        // Given
        String message = "test exception";
        ExceptionDocument exception1 = new ExceptionDocument("1", message, "2024-08-27T12:00:00");
        ExceptionDocument exception2 = new ExceptionDocument("2", message, "2024-08-27T13:00:00");
        List<ExceptionDocument> expectedExceptions = Arrays.asList(exception1, exception2);

        when(exceptionRepository.findByMessage(message)).thenReturn(expectedExceptions);

        // When
        List<ExceptionDocument> actualExceptions = exceptionService.findByMessage(message);

        // Then
        assertEquals(expectedExceptions, actualExceptions);
    }
}
