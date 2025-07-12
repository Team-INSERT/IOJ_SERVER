package com.insert.ioj.domain.subtask.domain.repository;

import com.insert.ioj.domain.submission.domain.Submission;
import com.insert.ioj.domain.subtask.domain.SubtaskResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubtaskResultRepository extends JpaRepository<SubtaskResult, Long> {
    List<SubtaskResult> findAllBySubmission(Submission subtaskId);
}
