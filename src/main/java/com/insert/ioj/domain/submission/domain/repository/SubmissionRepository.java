package com.insert.ioj.domain.submission.domain.repository;

import com.insert.ioj.domain.submission.domain.Submission;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SubmissionRepository extends CrudRepository<Submission, UUID> {

}
