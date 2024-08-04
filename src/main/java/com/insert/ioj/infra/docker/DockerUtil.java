package com.insert.ioj.infra.docker;

import com.insert.ioj.infra.cmd.CmdUtil;
import com.insert.ioj.infra.cmd.dto.res.ProcessOutput;

public class DockerUtil {
    public static final int BUILD_TIMEOUT = 30;
    public static final int RUN_TIMEOUT = 20;

    public static void buildImage(String dockerfilePath, String id, String contextPath) {
        String[] dockerCommand =
            new String[] {
                "docker",
                "image",
                "build",
                "-f", dockerfilePath,
                "-t", id,
                contextPath};
        CmdUtil.executeProcess(dockerCommand, BUILD_TIMEOUT);
    }

    public static ProcessOutput runContainer(String containerName) {
        String[] dockerCommand = new String[] {"docker", "run", "--rm", containerName};
        return CmdUtil.executeProcess(dockerCommand, RUN_TIMEOUT);
    }

    public static ProcessOutput runContainer(String containerName, String env) {
        String[] dockerCommand = new String[] {"docker", "run", "--rm", "-e", env, containerName};
        return CmdUtil.executeProcess(dockerCommand, RUN_TIMEOUT);
    }
}
