package com.insert.ioj.domain.problem.ProblemRoom.domain;

import com.insert.ioj.domain.problem.problem.domain.Problem;
import com.insert.ioj.domain.room.domain.Room;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ProblemRoom {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "problem_id")
    private Problem problem;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    public ProblemRoom(Problem problem, Room room) {
        this.problem = problem;
        this.room = room;
    }
}
