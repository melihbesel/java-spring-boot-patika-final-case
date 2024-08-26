package com.patika.indexservice.service;

import com.patika.indexservice.model.document.ExceptionDocument;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExceptionService {

    private final ElasticsearchOperations elasticsearchOperations;

    public void saveException(String message) {
        ExceptionDocument exceptionDocument = new ExceptionDocument(message, LocalDateTime.now().toString());
        elasticsearchOperations.save(exceptionDocument);
        log.info("exception kaydedildi. exception: {}", exceptionDocument);
    }

}
