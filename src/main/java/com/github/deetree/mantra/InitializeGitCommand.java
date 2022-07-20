package com.github.deetree.mantra;

import java.nio.file.Path;

/**
 * @author Mariusz Bal
 */
class InitializeGitCommand implements NativeCommand {

    private final OS os;
    private final Path projectPath;

    InitializeGitCommand(OS os, Path projectPath) {
        this.os = os;
        this.projectPath = projectPath;
    }

    @Override
    public Result execute() {
        if (execute(os, projectPath, "git init") != Result.OK)
            throw new ActionException("An exception occurred while git repository initialization");
        return Result.OK;
    }
}
