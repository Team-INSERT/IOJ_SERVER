package com.insert.ioj.domain.submission.domain.repository;

import com.insert.ioj.domain.submission.domain.Artifact;
import com.insert.ioj.domain.submission.domain.Submission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArtifactRepository extends JpaRepository<Artifact, Long> {
    List<Artifact> findAllBySubmission(Submission submission);
}
