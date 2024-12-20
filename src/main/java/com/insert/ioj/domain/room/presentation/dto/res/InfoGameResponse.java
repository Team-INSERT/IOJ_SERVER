package com.insert.ioj.domain.room.presentation.dto.res;

import com.insert.ioj.domain.room.domain.Room;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class InfoGameResponse {
    private Long userId;
    private String title;
    private LocalDateTime endTime;
    private List<Long> problems;

    public InfoGameResponse(Long userId, Room room, List<Long> problems) {
        this.userId = userId;
        this.title = room.getTitle();
        this.endTime = room.getEndTime();
        this.problems = problems;
    }
}
