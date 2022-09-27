package com.github.deetree.mantra.oscmd;

import com.github.deetree.mantra.ActionException;
import com.github.deetree.mantra.OS;
import com.github.deetree.mantra.Result;
import com.github.deetree.mantra.printer.Printer;

import java.nio.file.Path;

/**
 * @author Mariusz Bal
 */
class InitializeGitCommand implements NativeCommand {

    private final OS os;
    private final Path projectPath;
    private final Printer printer;

    InitializeGitCommand(OS os, Path projectPath, Printer printer) {
        this.os = os;
        this.projectPath = projectPath;
        this.printer = printer;
    }

    @Override
    public String preExecuteStatus() {
        return "Initializing local git repository";
    }

    @Override
    public Result execute() {
        if (execute(os, projectPath, "git init", printer) != Result.OK)
            throw new ActionException("An exception occurred during git repository initialization");
        return Result.OK;
    }

    @Override
    public String postExecuteStatus() {
        return "Git repository initialized successfully";
    }
}
