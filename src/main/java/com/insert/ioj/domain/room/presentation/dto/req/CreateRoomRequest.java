package com.insert.ioj.domain.room.presentation.dto.req;

import com.insert.ioj.domain.room.domain.Room;
import com.insert.ioj.domain.user.domain.User;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

@Getter
@NoArgsConstructor
public class CreateRoomRequest {
    @Size(max = 20, message = "title은 공백 포함 20자까지만 가능합니다.")
    @NotNull(message = "title이 비어있습니다.")
    private String title;

    @Range(min = 1, max = 8, message = "maxPeople은 1혹은 8사이어야 합니다.")
    @NotNull(message = "maxPeople이 비어있습니다.")
    private Integer maxPeople;

    @Range(min = 1, max = 5, message = "problem은 1혹은 5사이어야 합니다.")
    @NotNull(message = "problem이 비어있습니다.")
    private Integer problem;

    @Range(min = 1, max = 5, message = "minDifficulty는 1혹은 5사이어야 합니다.")
    @NotNull(message = "minDifficulty가 비어있습니다.")
    private Integer minDifficulty;

    @Range(min = 1, max = 5, message = "maxDifficulty는 1혹은 5사이어야 합니다.")
    @NotNull(message = "maxDifficulty가 비어있습니다.")
    private Integer maxDifficulty;

    @Range(min = 1, max = 60, message = "time은 1혹은 60사이어야 합니다.")
    @NotNull(message = "time이 비어있습니다.")
    private Integer time;

    public Room toEntity(User host) {
        return new Room(
            title,
            maxPeople,
            problem,
            minDifficulty,
            maxDifficulty,
            time,
            host
        );
    }
}
