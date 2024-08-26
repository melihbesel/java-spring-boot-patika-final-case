package com.patika.searchservice.repository;

import com.patika.searchservice.model.document.ExceptionDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface ExceptionRepository extends ElasticsearchRepository<ExceptionDocument, String> {
    List<ExceptionDocument> findByMessage(String message);
}
