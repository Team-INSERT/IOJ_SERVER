package com.insert.ioj.domain.error.domain.repository;

import com.insert.ioj.domain.error.domain.Error;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ErrorRepository extends JpaRepository<Error, Long> {
}
