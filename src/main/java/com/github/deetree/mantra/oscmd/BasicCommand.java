package com.github.deetree.mantra.oscmd;

import com.github.deetree.mantra.OS;
import com.github.deetree.mantra.Result;
import com.github.deetree.mantra.printer.Level;
import com.github.deetree.mantra.printer.Message;
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
    private final Printer printer;

    /**
     * Instantiate basic native command executor.
     *
     * @param projectPath  project directory path
     * @param os           operating system
     * @param gitUsername  local git username
     * @param gitUserEmail local git user email
     * @param printer      output printer
     */
    BasicCommand(Path projectPath, OS os, Printer printer) {
        this.projectPath = projectPath;
        this.os = os;
        this.printer = printer;
    }

    @Override
    public Result executeGit(String username, String email) {
        Stream.of(
                new InitializeGitCommand(os, projectPath, printer),
                new LocalGitUserConfigCommand(os, projectPath, username, email, printer),
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
        printer.print(new Message(Level.INFO, command.makePreExecuteStatus()));
        if (command.execute() == Result.OK)
            printer.print(new Message(Level.SUCCESS, command.makePostExecuteSuccessStatus()));
        else
            printer.print(new Message(Level.ERROR, command.makePostExecuteErrorStatus()));
    }
}
