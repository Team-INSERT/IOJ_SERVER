package com.insert.ioj.infra.status;

import com.insert.ioj.domain.Testcase.domain.Testcase;
import com.insert.ioj.domain.execution.domain.type.Verdict;
import com.insert.ioj.domain.problem.problem.domain.Problem;
import com.insert.ioj.domain.submission.domain.Submission;
import com.insert.ioj.global.constants.FileConstants;
import com.insert.ioj.global.constants.FolderConstants;
import com.insert.ioj.infra.file.FileUtil;
import com.insert.ioj.infra.testcase.TestcaseUtil;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VerificationUtil {
    private static final Pattern timePattern = Pattern.compile("time-wall:(\\d+)");

    public static Verdict verify(
        Submission submission, List<Testcase> testcases
    ) throws IOException {
        String basePath = FolderConstants.VOLUME_PATH + "/" + submission.getId().toString() + "/";
        Problem problem = submission.getProblem();

        for (int i = 0; i < testcases.size(); i++) {
            Testcase testcase = testcases.get(i);
            String metaResult = FileUtil.readFile(
                basePath + FileConstants.META_FILE.formatted(i)
            );

            Matcher timeMatcher = timePattern.matcher(metaResult);
            if (timeMatcher.find()) {
                int timeWall = Integer.parseInt(timeMatcher.group(1));

                if (timeWall > problem.getTimeLimit()) {
                    return Verdict.TIME_LIMIT_EXCEEDED;
                }
            }
            if (metaResult.contains("cg-oom-killed:")) {
                return Verdict.OUT_OF_MEMORY;
            }
            if (metaResult.contains("exitcode:0")) {
                String stdoutResult = FileUtil.readFile(
                    basePath + FileConstants.STDOUT_FILE.formatted(i)
                );

                String stdout = TestcaseUtil.processString(stdoutResult);
                if (!testcase.getOutput().equals(stdout)) {
                    return Verdict.WRONG_ANSWER;
                }
            } else {
                return Verdict.COMPILATION_ERROR;
            }
        };

        return Verdict.ACCEPTED;
    }
}
