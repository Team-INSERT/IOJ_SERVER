package com.insert.ioj.infra.status;

import com.insert.ioj.domain.Testcase.domain.Testcase;
import com.insert.ioj.domain.execution.domain.type.Verdict;
import com.insert.ioj.domain.submission.domain.Artifact;
import com.insert.ioj.infra.testcase.TestcaseUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VerificationUtil {
    private static final Pattern TIME_PATTERN = Pattern.compile("time-wall:([0-9.]+)");
    private static final Pattern CG_MEM_PATTERN = Pattern.compile("max-rss:(\\d+)");
    private static final Pattern EXIT_CODE_PATTERN = Pattern.compile("exitcode:(\\d+)");

    private static final String OOM_KEY = "cg-oom-killed";
    private static final String TIME_OUT_KEY = "status:TO";

    public static List<Artifact> evaluateTestcases(List<Artifact> artifacts, List<Testcase> testcases) {
        if (artifacts.size() != testcases.size()) {
            throw new IllegalArgumentException("Artifact 수와 Testcase 수가 일치하지 않습니다.");
        }

        List<Artifact> results = new ArrayList<>();
        for (int i = 0; i < artifacts.size(); i++) {
            Artifact result = evaluateTestcase(artifacts.get(i), testcases.get(i));
            results.add(result);
        }

        return results;
    }

    public static Artifact evaluateTestcase(Artifact artifact, Testcase testcase) {
        String meta = artifact.getMeta();
        String stdOutput = artifact.getStdout();

        Double executionTime = extractExecutionTime(meta);
        Integer memoryUsage = extractMemoryUsage(meta);
        Integer exitCode = extractExitCode(meta);

        Verdict verdict = determineVerdict(meta, stdOutput, testcase);

        return artifact.update(executionTime, memoryUsage, exitCode, verdict);
    }

    public static Verdict verify(List<Artifact> artifacts) {
        for (Artifact artifact : artifacts) {
            if (artifact.getVerdict() != Verdict.ACCEPTED) {
                return artifact.getVerdict();
            }
        }

        return Verdict.ACCEPTED;
    }

    private static Verdict determineVerdict(String meta, String stdOutput, Testcase testcase) {
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
        if (exitCode != 0) {
            return Verdict.RUNTIME_ERROR;
        }

        String processedOutput = TestcaseUtil.processString(stdOutput);
        return testcase.getOutput().equals(processedOutput)
            ? Verdict.ACCEPTED
            : Verdict.WRONG_ANSWER;
    }

    private static Double extractExecutionTime(String meta) {
        if (meta == null) return null;

        Matcher matcher = TIME_PATTERN.matcher(meta);
        if (matcher.find()) {
            try {
                return Double.parseDouble(matcher.group(1));
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return null;
    }

    private static Integer extractMemoryUsage(String meta) {
        if (meta == null) return null;

        Matcher matcher = CG_MEM_PATTERN.matcher(meta);
        if (matcher.find()) {
            try {
                return Integer.parseInt(matcher.group(1));
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return null;
    }

    private static Integer extractExitCode(String meta) {
        if (meta == null) return -111;

        Matcher matcher = EXIT_CODE_PATTERN.matcher(meta);
        if (matcher.find()) {
            try {
                return Integer.parseInt(matcher.group(1));
            } catch (NumberFormatException e) {
                return -111;
            }
        }
        return -111;
    }
}
