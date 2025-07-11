package com.insert.ioj.domain.subtask.domain.repository;

import com.insert.ioj.domain.subtask.domain.SubtaskResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubtaskResultRepository extends JpaRepository<SubtaskResult, Long> {
}
