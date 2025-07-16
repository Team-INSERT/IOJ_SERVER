package com.insert.ioj.domain.problem.problemContest.domain.repository;

import com.insert.ioj.domain.problem.problemContest.domain.ProblemContest;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProblemContestRepository extends CrudRepository<ProblemContest, Long>, CustomProblemContestRepository {
    List<ProblemContest> findAllByContest_Id(Long contestId);
}
