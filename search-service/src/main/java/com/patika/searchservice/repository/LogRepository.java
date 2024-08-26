package com.patika.searchservice.repository;

import com.patika.searchservice.model.document.LogDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface LogRepository extends ElasticsearchRepository<LogDocument, String> {
    List<LogDocument> findByMessage(String message);
}
