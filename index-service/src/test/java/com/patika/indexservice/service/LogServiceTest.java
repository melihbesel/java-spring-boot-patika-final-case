package com.patika.indexservice.service;

import com.patika.indexservice.model.document.LogDocument;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.ArgumentCaptor;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class LogServiceTest {

    @Mock
    private ElasticsearchOperations elasticsearchOperations;

    @InjectMocks
    private LogService logService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void it_should_save_log() {
        // Given
        String message = "Test log message";
        LocalDateTime now = LocalDateTime.now();
        LogDocument expectedDocument = new LogDocument(message, now.toString());

        // When
        logService.saveLog(message);

        // Then
        ArgumentCaptor<LogDocument> captor = ArgumentCaptor.forClass(LogDocument.class);
        verify(elasticsearchOperations, times(1)).save(captor.capture());
        LogDocument capturedDocument = captor.getValue();

        // Assert
        assertNotNull(capturedDocument);
        assertEquals(message, capturedDocument.getMessage());

    }
}
