package com.patika.searchservice.service;

import com.patika.searchservice.model.document.LogDocument;
import com.patika.searchservice.repository.LogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LogService {

    private final LogRepository logRepository;

    public List<LogDocument> findByMessage(String message){
        return logRepository.findByMessage(message);
    }

}
