package com.insert.ioj.infra.runner;

import com.insert.ioj.infra.file.FileUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RunnerUtil {

    public static List<String> readArtifacts(
        UUID id, int testcaseCnt, String fileName, String volumePath
    ) throws IOException {
        List<String> results = new ArrayList<>();
        String basePath = volumePath + "/" + id + "/results/";

        for (int i=0; i < testcaseCnt; i++) {
            String result = FileUtil.readFile(
                basePath + fileName.formatted(i)
            );

            result = result.replace("vol/"+id+"/", "");
            results.add(i, result);
        }

        return results;
    }
}
