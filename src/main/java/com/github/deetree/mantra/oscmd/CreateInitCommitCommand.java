package com.github.deetree.mantra.oscmd;

import com.github.deetree.mantra.ActionException;
import com.github.deetree.mantra.OS;
import com.github.deetree.mantra.Result;

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
    public String preExecuteStatus() {
        return "Creating initial commit";
    }

    @Override
    public Result execute() {
        if (execute(os, projectPath, "git add . && git commit -m \"Initial commit\"") != Result.OK)
            throw new ActionException("An exception occurred during initial git commit creation");
        return Result.OK;
    }

    @Override
    public String postExecuteStatus() {
        return "Initial commit created";
    }
}
