package com.insert.ioj.domain.room.presentation.dto.res;

import com.insert.ioj.domain.room.domain.Room;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class ListRoomResponse {
    private UUID id;
    private String title;
    private Integer maxPeople;
    private Integer problem;
    private Integer minDifficulty;
    private Integer maxDifficulty;
    private Integer time;

    public ListRoomResponse(Room room) {
        this.id = room.getId();
        this.title = room.getTitle();
        this.maxPeople = room.getMaxPeople();
        this.problem = room.getProblem();
        this.minDifficulty = room.getMinDifficulty();
        this.maxDifficulty = room.getMaxDifficulty();
        this.time = room.getTime();
    }
}
