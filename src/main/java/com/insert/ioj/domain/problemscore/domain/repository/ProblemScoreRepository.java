package com.insert.ioj.domain.problemscore.domain.repository;

import com.insert.ioj.domain.contest.domain.Contest;
import com.insert.ioj.domain.problemscore.domain.ProblemScore;
import com.insert.ioj.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProblemScoreRepository extends JpaRepository<ProblemScore, Long>, CustomProblemScoreRepository {
    Optional<ProblemScore> findByProblemIdAndContestAndUser(Long problemId, Contest contest, User user);
}
