package com.insert.ioj.infra.docker;

import com.insert.ioj.infra.cmd.CmdUtil;
import com.insert.ioj.infra.cmd.dto.res.ProcessOutput;

public class DockerUtil {
    public static final int BUILD_TIMEOUT = 60;
    public static final int RUN_TIMEOUT = 60;

    public static void buildImage(String id) {
        String[] dockerCommand = new String[] {"docker", "image", "build", "util", "-t", id};
        CmdUtil.executeProcess(dockerCommand, BUILD_TIMEOUT);
    }

    public static ProcessOutput runContainer(String containerName) {
        String[] dockerCommand = new String[] {"docker", "run", "--rm", containerName};
        return CmdUtil.executeProcess(dockerCommand, RUN_TIMEOUT);
    }
}
