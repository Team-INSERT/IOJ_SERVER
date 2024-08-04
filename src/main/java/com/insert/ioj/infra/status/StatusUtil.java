package com.insert.ioj.infra.status;


import com.insert.ioj.domain.execution.domain.type.Verdict;

public class StatusUtil {
    public static final int ACCEPTED_OR_WRONG_ANSWER_STATUS = 0;
    public static final int COMPILATION_ERROR_STATUS = 96;
    public static final int OUT_OF_MEMORY_STATUS = 139;
    public static final int TIME_LIMIT_EXCEEDED_STATUS = 124;

    public static Verdict statusResponse(int status, boolean ans) {
        switch (status) {
            case ACCEPTED_OR_WRONG_ANSWER_STATUS:
                if (ans)
                    return Verdict.ACCEPTED;
                else
                    return Verdict.WRONG_ANSWER;
            case COMPILATION_ERROR_STATUS:
                return Verdict.COMPILATION_ERROR;
            case OUT_OF_MEMORY_STATUS:
                return Verdict.OUT_OF_MEMORY;
            case TIME_LIMIT_EXCEEDED_STATUS:
                return Verdict.TIME_LIMIT_EXCEEDED;
            default:
                return Verdict.RUNTIME_ERROR;
        }
    }
}
