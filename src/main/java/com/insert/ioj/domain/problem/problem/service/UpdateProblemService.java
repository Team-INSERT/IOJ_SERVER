package com.insert.ioj.domain.problem.problem.service;

import com.insert.ioj.domain.problem.problem.domain.Problem;
import com.insert.ioj.domain.problem.problem.domain.repository.ProblemRepository;
import com.insert.ioj.domain.problem.problem.presentation.dto.req.UpdateProblemRequest;
import com.insert.ioj.global.error.exception.ErrorCode;
import com.insert.ioj.global.error.exception.IojException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UpdateProblemService {
    private final ProblemRepository problemRepository;
    private final CacheManager cacheManager;

    @Transactional
    @CacheEvict(value = "problems", key = "#request.id")
    public void execute(UpdateProblemRequest request) {
        Problem problem = problemRepository.findById(request.id())
            .orElseThrow(() -> new IojException(ErrorCode.NOT_FOUND_PROBLEM));

        problem.update(request.content(), request.inputContent(), request.outputContent());
        evictRelatedCaches(request.id());
    }

    private void evictRelatedCaches(Long problemId) {
        Cache cache = cacheManager.getCache("problems");
        if (cache != null) {
            cache.evict(problemId);
        }
    }
}
