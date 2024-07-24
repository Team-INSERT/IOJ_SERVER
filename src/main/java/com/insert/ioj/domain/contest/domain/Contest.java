package com.insert.ioj.domain.contest.domain;

import com.insert.ioj.domain.user.domain.type.Authority;
import com.insert.ioj.global.error.exception.ErrorCode;
import com.insert.ioj.global.error.exception.IojException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Contest {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    public Contest(String title, LocalDateTime startTime, LocalDateTime endTime, Authority authority) {
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
        this.authority = authority;
    }

    public void checkRole(Authority authority) {
        if (this.authority == null) return;
        if (this.authority == authority) return;
        if (this.authority == Authority.ADMIN) return;

        switch (authority) {
            case USER -> throw new IojException(ErrorCode.FORBIDDEN_USER);
            case THIRD_YEAR -> throw new IojException(ErrorCode.FORBIDDEN_THIRD_YEAR);
            case SECOND_YEAR -> throw new IojException(ErrorCode.FORBIDDEN_SECOND_YEAR);
            case FIRST_YEAR -> throw new IojException(ErrorCode.FORBIDDEN_FIRST_YEAR);
        }
    }
}
