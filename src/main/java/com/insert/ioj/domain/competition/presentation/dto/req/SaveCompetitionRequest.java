package com.insert.ioj.domain.competition.presentation.dto.req;

import com.insert.ioj.domain.competition.domain.Competition;
import com.insert.ioj.domain.user.domain.type.Authority;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class SaveCompetitionRequest {
    @Size(max = 22, message = "title은 공백 포함 22자까지만 가능합니다.")
    @NotNull(message = "title이 비어있습니다.")
    private String title;

    @Future(message = "현재 시간보다 미래 날짜여야 합니다.")
    @NotNull(message = "startTime이 비어있습니다.")
    private LocalDateTime startTime;

    @Future(message = "현재 시간보다 미래 날짜여야 합니다.")
    @NotNull(message = "endTime이 비어있습니다.")
    private LocalDateTime endTime;

    @NotNull(message = "authority가 비어있습니다.")
    private Authority authority;

    @NotNull(message = "problems가 비어있습니다.")
    private List<Long> problems;

    public Competition toEntity() {
        return new Competition(
            title, startTime, endTime, authority
        );
    }
}
