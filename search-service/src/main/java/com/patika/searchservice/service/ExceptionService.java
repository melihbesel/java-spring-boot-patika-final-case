package com.patika.searchservice.service;

import com.patika.searchservice.model.document.ExceptionDocument;
import com.patika.searchservice.repository.ExceptionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExceptionService {

    private final ExceptionRepository exceptionRepository;

    public List<ExceptionDocument> findByMessage(String message){
        return exceptionRepository.findByMessage(message);
    }

}
