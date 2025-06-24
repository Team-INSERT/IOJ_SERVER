package com.insert.ioj.domain.submission.domain.repository;

import com.insert.ioj.domain.submission.domain.Artifact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtifactRepository extends JpaRepository<Artifact, Long> {
}
