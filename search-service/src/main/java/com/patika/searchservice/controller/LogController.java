package com.patika.searchservice.controller;

import com.patika.searchservice.model.document.LogDocument;
import com.patika.searchservice.service.LogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/search/logs")
@RequiredArgsConstructor
public class LogController {

    private final LogService logService;

    @GetMapping("/{message}")
    public List<LogDocument> findByMessage(@PathVariable("message") String message) {
        return logService.findByMessage(message);
    }

}