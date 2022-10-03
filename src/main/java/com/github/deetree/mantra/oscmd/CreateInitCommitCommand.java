package com.github.deetree.mantra.oscmd;

import com.github.deetree.mantra.ActionException;
import com.github.deetree.mantra.OS;
import com.github.deetree.mantra.Result;
import com.github.deetree.mantra.printer.Printer;

import java.nio.file.Path;

/**
 * Initial commit creating native command.
 *
 * @author Mariusz Bal
 */
class CreateInitCommitCommand implements NativeCommand {

    private final OS os;
    private final Path projectPath;
    private final Printer printer;

    CreateInitCommitCommand(OS os, Path projectPath, Printer printer) {
        this.os = os;
        this.projectPath = projectPath;
        this.printer = printer;
    }

    @Override
    public String makePreExecuteStatus() {
        return "Creating initial commit";
    }

    @Override
    public Result execute() {
        if (execute(os, projectPath, "git add . && git commit -m \"Initial commit\"", printer) != Result.OK)
            throw new ActionException("An exception occurred during initial git commit creation");
        return Result.OK;
    }

    @Override
    public String makePostExecuteSuccessStatus() {
        return "Initial commit created";
    }

    @Override
    public String makePostExecuteErrorStatus() {
        return "Initial commit could not be created";
    }
}
