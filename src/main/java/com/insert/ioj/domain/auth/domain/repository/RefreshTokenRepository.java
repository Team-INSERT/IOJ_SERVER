package com.insert.ioj.domain.auth.domain.repository;

import com.insert.ioj.domain.auth.domain.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
}
