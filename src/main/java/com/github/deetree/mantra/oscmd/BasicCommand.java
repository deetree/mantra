package com.github.deetree.mantra.oscmd;

import com.github.deetree.mantra.OS;
import com.github.deetree.mantra.Result;
import com.github.deetree.mantra.printer.Level;
import com.github.deetree.mantra.printer.Printer;

import java.nio.file.Path;
import java.util.stream.Stream;

/**
 * Entry point for executing native commands.
 *
 * @author Mariusz Bal
 */
class BasicCommand implements Command {

    private final Path projectPath;
    private final OS os;
    private final String gitUsername;
    private final String gitUserEmail;
    private final Printer printer;

    BasicCommand(Path projectPath, OS os, String gitUsername, String gitUserEmail, Printer printer) {
        this.projectPath = projectPath;
        this.os = os;
        this.gitUsername = gitUsername;
        this.gitUserEmail = gitUserEmail;
        this.printer = printer;
    }

    @Override
    public Result executeGit() {
        Stream.of(
                new InitializeGitCommand(os, projectPath, printer),
                new LocalGitUserConfigCommand(os, projectPath, gitUsername, gitUserEmail, printer),
                new CreateInitCommitCommand(os, projectPath, printer)
        ).forEach(this::executeCommand);
        return Result.OK;
    }

    @Override
    public Result openIntelliJ() {
        executeCommand(new OpenIntelliJCommand(os, projectPath, printer));
        return Result.OK;
    }

    @Override
    public Result locateIntelliJ(Path launcherPathFile) {
        executeCommand(new LocateIntelliJCommand(os, launcherPathFile, printer));
        return Result.OK;
    }

    private void executeCommand(NativeCommand command) {
        printer.print(Level.INFO, command.makePreExecuteStatus());
        if (command.execute() == Result.OK)
            printer.print(Level.SUCCESS, command.makePostExecuteSuccessStatus());
        else
            printer.print(Level.ERROR, command.makePostExecuteErrorStatus());
    }
}
