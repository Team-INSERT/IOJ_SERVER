package com.insert.ioj.domain.room.service;

import com.insert.ioj.domain.entry.domain.repository.EntryRepository;
import com.insert.ioj.domain.item.domain.repository.CustomUserItemRepository;
import com.insert.ioj.domain.problem.ProblemRoom.domain.repository.ProblemRoomRepository;
import com.insert.ioj.domain.room.domain.Room;
import com.insert.ioj.domain.room.facade.RoomFacade;
import com.insert.ioj.domain.room.presentation.dto.res.ResultResponse;
import com.insert.ioj.domain.solve.room.repository.CustomSolveRoomRepository;
import com.insert.ioj.domain.user.domain.User;
import com.insert.ioj.domain.user.facade.UserFacade;
import com.insert.ioj.global.error.exception.ErrorCode;
import com.insert.ioj.global.error.exception.IojException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ResultService {
    private final UserFacade userFacade;
    private final RoomFacade roomFacade;
    private final EntryRepository entryRepository;
    private final CustomSolveRoomRepository solveRoomRepository;
    private final ProblemRoomRepository problemRoomRepository;
    private final CustomUserItemRepository userItemRepository;

    @Transactional
    public List<ResultResponse> execute(UUID roomId) {
        User user = userFacade.getCurrentUser();
        Room room = roomFacade.getRoom(roomId);

        room.isFinished();

        entryRepository.findByRoomAndUser(room, user)
            .orElseThrow(() -> new IojException(ErrorCode.NOT_FOUND_ROOM_IN_USER));

        int totalProblem = problemRoomRepository.findByRoom(room).size();

        return entryRepository.findAllByRoom(room).stream()
            .map(entry ->
                new ResultResponse(
                    entry,
                    solveRoomRepository.countByCorrectProblem(room, user),
                    totalProblem,
                    userItemRepository.countByUseItem(room, user),
                    entry.getProtectCnt()
            )).toList();
    }
}
