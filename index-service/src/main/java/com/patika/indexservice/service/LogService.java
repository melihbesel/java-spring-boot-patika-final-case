package com.patika.indexservice.service;

import com.patika.indexservice.model.document.LogDocument;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class LogService {

    private final ElasticsearchOperations elasticsearchOperations;

    public void saveLog(String message) {
        LogDocument logDocument = new LogDocument(message, LocalDateTime.now().toString());
        elasticsearchOperations.save(logDocument);
        log.info("log kaydedildi. log: {}", logDocument);
    }

}
