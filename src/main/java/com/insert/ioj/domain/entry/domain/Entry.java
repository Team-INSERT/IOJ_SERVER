package com.insert.ioj.domain.entry.domain;

import com.insert.ioj.domain.room.domain.Room;
import com.insert.ioj.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Entry {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Boolean ready;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;
}
