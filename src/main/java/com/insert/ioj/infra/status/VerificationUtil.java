package com.insert.ioj.infra.status;

import com.insert.ioj.domain.Testcase.domain.Testcase;
import com.insert.ioj.domain.execution.domain.type.Verdict;
import com.insert.ioj.domain.submission.domain.Artifact;
import com.insert.ioj.infra.testcase.TestcaseUtil;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VerificationUtil {
    private static final Pattern EXIT_CODE_PATTERN = Pattern.compile("exitcode:(\\d+)");
    private static final String OOM_KEY = "cg-oom-killed";
    private static final String TIME_OUT_KEY = "status:TO";

    public static Verdict verify(List<Artifact> artifacts, List<Testcase> testcases) {
        if (artifacts.size() != testcases.size()) {
            throw new IllegalArgumentException("Artifact 수와 Testcase 수가 일치하지 않습니다.");
        }

        for (int i = 0; i < artifacts.size(); i++) {
            Verdict result = evaluateTestcase(artifacts.get(i), testcases.get(i));
            if (result != Verdict.ACCEPTED) {
                return result;
            }
        }
        return Verdict.ACCEPTED;
    }

    public static Verdict evaluateTestcase(Artifact artifact, Testcase testcase) {
        String meta        = artifact.getMeta();
        String stdOutput   = artifact.getStdout();

        if (meta == null) {
            return Verdict.COMPILATION_ERROR;
        }

        if (meta.contains(TIME_OUT_KEY)) {
            return Verdict.TIME_LIMIT_EXCEEDED;
        }

        if (meta.contains(OOM_KEY)) {
            return Verdict.OUT_OF_MEMORY;
        }

        Integer exitCode = extractExitCode(meta);
        if (exitCode == null || exitCode != 0) {
            return Verdict.RUNTIME_ERROR;
        }

        String processedOutput = TestcaseUtil.processString(stdOutput);
        return testcase.getOutput().equals(processedOutput)
                ? Verdict.ACCEPTED
                : Verdict.WRONG_ANSWER;
    }

    private static Integer extractExitCode(String meta) {
        Matcher matcher = EXIT_CODE_PATTERN.matcher(meta);
        return matcher.find() ? Integer.parseInt(matcher.group(1)) : null;
    }
}
