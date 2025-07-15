package com.insert.ioj.domain.error.controller;

import com.insert.ioj.domain.error.domain.Error;
import com.insert.ioj.domain.error.domain.repository.ErrorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/errors")
@RestController
@Transactional
public class ErrorController {
    private final ErrorRepository errorRepository;

    @GetMapping("/{submission-id}")
    public void error(
        @PathVariable("submission-id") String id
    ) {
        errorRepository.save(new Error(id));
    }
}
