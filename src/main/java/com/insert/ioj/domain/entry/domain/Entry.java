package com.insert.ioj.domain.entry.domain;

import com.insert.ioj.domain.room.domain.Room;
import com.insert.ioj.domain.user.domain.User;
import com.insert.ioj.global.entity.BaseTimeEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Entry extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Boolean ready = false;
    private Boolean host = false;
    private Integer correctProblem = 0;
    private Integer useItemCnt = 0;
    private Integer protectCnt = 0;
    private LocalTime finishTime;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    public Entry(Room room, User user) {
        this.room = room;
        this.user = user;
    }

    public Entry(Room room, User user, Boolean host) {
        this.room = room;
        this.user = user;
        this.host = host;
        this.ready = true;
    }

    public Boolean changeReady() {
        ready = !ready;
        return ready;
    }

    public void successProtect(Boolean isProtect) {
        if (isProtect) {
            protectCnt++;
        }
        useItemCnt++;
    }

    public void useItem() {
        useItemCnt++;
    }
}
