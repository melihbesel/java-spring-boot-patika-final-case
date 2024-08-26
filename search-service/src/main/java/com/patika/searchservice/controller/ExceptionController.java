package com.patika.searchservice.controller;

import com.patika.searchservice.model.document.ExceptionDocument;
import com.patika.searchservice.service.ExceptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/search/exceptions")
@RequiredArgsConstructor
public class ExceptionController {

    private final ExceptionService exceptionService;

    @GetMapping("/{message}")
    public List<ExceptionDocument> findByMessage(@PathVariable("message") String message) {
        return exceptionService.findByMessage(message);
    }

}