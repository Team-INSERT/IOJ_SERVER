package com.insert.ioj.domain.room.presentation.dto.req;

import com.insert.ioj.domain.execution.language.Language;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class SubmitRoomRequest {
    @NotNull(message = "roomId가 비어있습니다.")
    private UUID roomId;

    @NotNull(message = "problemId가 비어있습니다.")
    private Long problemId;

    @NotNull(message = "sourcecode가 비어있습니다.")
    private String sourcecode;

    @NotNull(message = "language가 비어있습니다.")
    private Language language;
}
