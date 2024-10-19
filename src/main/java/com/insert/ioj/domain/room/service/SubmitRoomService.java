package com.insert.ioj.domain.room.service;

import com.insert.ioj.domain.Testcase.domain.Testcase;
import com.insert.ioj.domain.Testcase.domain.repository.TestcaseRepository;
import com.insert.ioj.domain.entry.domain.repository.EntryRepository;
import com.insert.ioj.domain.execution.domain.Execution;
import com.insert.ioj.domain.execution.domain.ExecutionFactory;
import com.insert.ioj.domain.execution.domain.type.Verdict;
import com.insert.ioj.domain.execution.presentation.dto.res.TestcaseResult;
import com.insert.ioj.domain.execution.service.ExecutionService;
import com.insert.ioj.domain.problem.domain.Problem;
import com.insert.ioj.domain.problem.domain.repository.ProblemRepository;
import com.insert.ioj.domain.room.domain.Room;
import com.insert.ioj.domain.room.facade.RoomFacade;
import com.insert.ioj.domain.room.presentation.dto.req.SubmitRoomRequest;
import com.insert.ioj.domain.solve.room.SolveRoom;
import com.insert.ioj.domain.solve.room.repository.CustomSolveRoomRepository;
import com.insert.ioj.domain.solve.room.repository.SolveRoomRepository;
import com.insert.ioj.domain.user.domain.User;
import com.insert.ioj.domain.user.facade.UserFacade;
import com.insert.ioj.global.constants.FileConstants;
import com.insert.ioj.global.error.exception.ErrorCode;
import com.insert.ioj.global.error.exception.IojException;
import com.insert.ioj.infra.cmd.dto.res.ProcessOutput;
import com.insert.ioj.infra.docker.DockerUtil;
import com.insert.ioj.infra.testcase.TestcaseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SubmitRoomService {
    private final UserFacade userFacade;
    private final RoomFacade roomFacade;
    private final ProblemRepository problemRepository;
    private final EntryRepository entryRepository;
    private final TestcaseRepository testcaseRepository;
    private final SolveRoomRepository solveRoomRepository;
    private final ExecutionService executionService;
    private final CustomSolveRoomRepository customSolveRoomRepository;

    public Verdict execute(SubmitRoomRequest request) {
        Problem problem = problemRepository.findById(request.getProblemId())
            .orElseThrow(() -> new IojException(ErrorCode.NOT_FOUND_PROBLEM));
        List<Testcase> testcases = testcaseRepository.findAllByProblem(problem)
            .orElseThrow(() -> new IojException(ErrorCode.NOT_FOUND_PROBLEM));
        User user = userFacade.getCurrentUser();
        Room room = roomFacade.getRoom(request.getRoomId());

        room.isActive();
        notInUser(user, room);
        existsCorrectProblem(room, user, problem);

        Execution execution = ExecutionFactory.createExecution(
            request.getSourcecode(),
            testcases,
            problem.getTimeLimit(),
            problem.getMemoryLimit(),
            request.getLanguage()
        );

        Verdict verdict = Verdict.ACCEPTED;
        createEnvironmentAndBuild(execution);
        for (Testcase testcase : testcases) {
            TestcaseResult testcaseResult = getTestcaseResult(execution, testcase);

            verdict = testcaseResult.getVerdict();
            if (testcaseResult.getVerdict() != Verdict.ACCEPTED)
                break;
        }
        solveRoomRepository.save(new SolveRoom(
            user, room, problem, request.getSourcecode(), verdict, request.getLanguage()));

        DockerUtil.deleteImage(execution.getImageName());
        deleteEnvironment(execution);

        return verdict;
    }

    private void existsCorrectProblem(Room room, User user, Problem problem) {
        Boolean isCorrect = customSolveRoomRepository.existsByCorrectProblem(room, user, problem);
        if (isCorrect)
            throw new IojException(ErrorCode.ALREADY_SOLVED_PROBLEM);
    }

    private void notInUser(User user, Room room) {
        Boolean isUser = entryRepository.existsByUserAndRoom(user, room);
        if (!isUser) {
            throw new IojException(ErrorCode.NOT_FOUND_ROOM_IN_USER);
        }
    }

    private TestcaseResult getTestcaseResult(Execution execution, Testcase testcase) {
        try {
            ProcessOutput processOutput = executionService.run(execution, testcase.getId().toString());
            return TestcaseUtil.testcaseResponse(processOutput, testcase);
        } catch (Exception e) {
            return new TestcaseResult(
                Verdict.TIME_LIMIT_EXCEEDED,
                "",
                "실행이 시간 제한을 초과했습니다.",
                testcase,
                execution.getTimeLimit() + 1
            );
        }
    }

    private void createEnvironmentAndBuild(Execution execution) {
        buildExecutionEnvironment(execution);
        execution.createEntrypointFiles();

        String dockerfilePath = execution.getPath() + "/" + FileConstants.DOCKER_FILE_NAME;
        DockerUtil.buildImage(dockerfilePath, execution.getImageName(), execution.getPath());
    }

    private void buildExecutionEnvironment(Execution execution) {
        try {
            execution.createExecutionDirectory();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void deleteEnvironment(Execution execution) {
        try {
            execution.deleteExecutionDirectory();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
