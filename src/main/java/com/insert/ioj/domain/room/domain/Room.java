package com.insert.ioj.domain.room.domain;

import com.insert.ioj.domain.room.domain.type.RoomStatus;
import com.insert.ioj.domain.user.domain.User;
import com.insert.ioj.global.entity.BaseTimeEntity;
import com.insert.ioj.global.error.exception.ErrorCode;
import com.insert.ioj.global.error.exception.IojException;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Room extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String title;
    private int maxPeople;
    private int problem;
    private int minDifficulty;
    private int maxDifficulty;
    private int time;
    private int peopleCnt;
    private LocalDateTime endTime;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User host;

    @Enumerated(EnumType.STRING)
    private RoomStatus status = RoomStatus.RECRUITING;

    public Room(String title,
                int maxPeople,
                int problem,
                int minDifficulty,
                int maxDifficulty,
                int time,
                User host) {
        this.title = title;
        this.maxPeople = maxPeople;
        this.problem = problem;
        this.minDifficulty = minDifficulty;
        this.maxDifficulty = maxDifficulty;
        this.time = time;
        this.host = host;
        this.peopleCnt = 1;
    }

    public void checkHost(User user) {
        if (host != user) {
            throw new IojException(ErrorCode.FORBIDDEN_ROOM);
        }
    }

    public void updateStatus() {
        if (status == RoomStatus.STARTED) {
            throw new IojException(ErrorCode.STARTED_ROOM);
        }
        this.status = RoomStatus.STARTED;
    }

    public void addPeople(User user) {
        if (peopleCnt >= maxPeople) {
            throw new IojException(ErrorCode.FULL_ROOM);
        } else if (host == user) {
            throw new IojException(ErrorCode.ALREADY_USER);
        }
        peopleCnt++;
    }

    public void removePeople() {
        peopleCnt--;
    }

    public void isActive() {
        switch (status) {
            case RECRUITING -> throw new IojException(ErrorCode.NOT_STARTED_ROOM);
            case ENDED -> throw new IojException(ErrorCode.FINISHED_ROOM);
        }
    }

    public void startRoom() {
        endTime = LocalDateTime.now().plusMinutes(time);
    }
}
