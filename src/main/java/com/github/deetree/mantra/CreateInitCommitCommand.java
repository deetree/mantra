package com.github.deetree.mantra;

import java.nio.file.Path;

/**
 * @author Mariusz Bal
 */
class CreateInitCommitCommand implements NativeCommand {

    private final OS os;
    private final Path projectPath;

    CreateInitCommitCommand(OS os, Path projectPath) {
        this.os = os;
        this.projectPath = projectPath;
    }

    @Override
    public Result execute() {
        if (execute(os, projectPath, "git add . && git commit -m 'Initial commit'") != Result.OK)
            throw new ActionException("An exception occurred during initial git commit creation");
        return Result.OK;
    }
}
