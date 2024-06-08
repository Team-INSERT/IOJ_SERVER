package com.insert.ioj.domain.user.facade;

import com.insert.ioj.domain.user.domain.User;
import com.insert.ioj.domain.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserFacade {
    private final UserRepository userRepository;

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
            .orElseThrow();
    }
}
