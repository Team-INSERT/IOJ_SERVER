package com.insert.ioj.domain.room.service;

import com.insert.ioj.domain.entry.domain.Entry;
import com.insert.ioj.domain.entry.domain.repository.EntryRepository;
import com.insert.ioj.domain.item.service.ItemGiveService;
import com.insert.ioj.domain.problem.problem.domain.Problem;
import com.insert.ioj.domain.problem.problem.domain.repository.CustomProblemRepository;
import com.insert.ioj.domain.problem.ProblemRoom.domain.ProblemRoom;
import com.insert.ioj.domain.problem.problemContest.domain.repository.ProblemRoomRepository;
import com.insert.ioj.domain.room.domain.Room;
import com.insert.ioj.domain.room.facade.RoomFacade;
import com.insert.ioj.domain.room.presentation.dto.res.StartGameResponse;
import com.insert.ioj.domain.user.domain.User;
import com.insert.ioj.domain.user.facade.UserFacade;
import com.insert.ioj.global.error.exception.ErrorCode;
import com.insert.ioj.global.error.exception.IojException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class StartGameService {
    private final RoomFacade roomFacade;
    private final UserFacade userFacade;
    private final EntryRepository entryRepository;
    private final ItemGiveService itemGiveService;
    private final CustomProblemRepository problemRepository;
    private final ProblemRoomRepository problemRoomRepository;

    @Transactional
    public StartGameResponse execute(UUID roomId) {
        Room room = roomFacade.getRoom(roomId);
        User user = userFacade.getCurrentUser();

        room.checkHost(user);
        checkReadyUser(room);
        room.updateStatus();
        saveProblems(room);
        room.startRoom();

        List<Entry> entry = entryRepository.findAllByRoom(room);
        itemGiveService.execute(room, entry);

        return new StartGameResponse();
    }

    private void saveProblems(Room room) {
        List<Problem> problems = problemRepository.getBetweenLevelProblems(room.getMinDifficulty(), room.getMaxDifficulty());
        Collections.shuffle(problems);
        problems.subList(room.getProblem(), problems.size()).clear();

        for (Problem problem : problems) {
            problemRoomRepository.save(new ProblemRoom(problem, room));
        }
    }

    private void checkReadyUser(Room room) {
        Long cntIsReady = entryRepository.countIsReady(room)
            .orElseThrow(() -> new IojException(ErrorCode.NOT_FOUND_ROOM_IN_USER));
        if (room.getMaxPeople()-1 == cntIsReady) {
            throw new IojException(ErrorCode.NOT_READY_ROOM);
        }
    }
}
