package com.insert.ioj.domain.solve.room;


import com.insert.ioj.domain.execution.domain.type.Verdict;
import com.insert.ioj.domain.execution.language.Language;
import com.insert.ioj.domain.problem.problem.domain.Problem;
import com.insert.ioj.domain.room.domain.Room;
import com.insert.ioj.domain.solve.solve.Solve;
import com.insert.ioj.domain.user.domain.User;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class SolveRoom extends Solve {
    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    public SolveRoom(User user,
                     Room room,
                     Problem problem,
                     String sourcecode,
                     Verdict verdict,
                     Language language) {
        super(user, problem, sourcecode, verdict, language);
        this.room = room;
    }
}
