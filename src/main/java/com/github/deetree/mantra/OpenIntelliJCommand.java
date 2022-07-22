package com.github.deetree.mantra;

import java.nio.file.Path;

/**
 * @author Mariusz Bal
 */
class OpenIntelliJCommand implements NativeCommand {

    private final OS os;
    private final Path projectPath;

    OpenIntelliJCommand(OS os, Path projectPath) {
        this.os = os;
        this.projectPath = projectPath;
    }

    @Override
    public Result execute() {
        if (execute(os, projectPath, "$(locate idea.sh) %s > /dev/null 2>&1 &".formatted(projectPath)) != Result.OK)
            throw new ActionException("An exception occurred during IntelliJ IDEA opening");
        return Result.OK;
    }
}
