package com.patika.indexservice.service;

import com.patika.indexservice.model.document.ExceptionDocument;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ExceptionServiceTest {

    @Mock
    private ElasticsearchOperations elasticsearchOperations;

    @InjectMocks
    private ExceptionService exceptionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void it_should_save_exception() {
        // Given
        String message = "Test exception message";
        LocalDateTime now = LocalDateTime.now();
        ExceptionDocument expectedDocument = new ExceptionDocument(message, now.toString());

        // When
        exceptionService.saveException(message);

        // Then

        verify(elasticsearchOperations, times(1)).save(any(ExceptionDocument.class));

    }
}
