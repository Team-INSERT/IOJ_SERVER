package com.insert.ioj.domain.room.presentation.dto.res;

import com.insert.ioj.domain.room.domain.Room;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class InfoRoomResponse {
    private String title;
    private int time;
    private int maxPeople;
    private List<UserInfo> users;

    public InfoRoomResponse(Room room, List<UserInfo> users) {
        this.title = room.getTitle();
        this.time = room.getTime();
        this.maxPeople = room.getMaxPeople();
        this.users = users;
    }
}
