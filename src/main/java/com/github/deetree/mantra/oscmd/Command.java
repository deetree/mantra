package com.github.deetree.mantra.oscmd;

import com.github.deetree.mantra.OS;
import com.github.deetree.mantra.Result;
import com.github.deetree.mantra.printer.Printer;

import java.nio.file.Path;

/**
 * Native shell command executing.
 *
 * @author Mariusz Bal
 */
public interface Command {

    /**
     * Static factory method creating object for executing shell commands.
     *
     * @param projectPath  project path
     * @param os           operating system
     * @param gitUsername  local git config username
     * @param gitUserEmail local git config user email
     * @param printer      printer
     * @return {@link BasicCommand} implementing native commands executing methods
     */
    static Command getDefault(Path projectPath, OS os, String gitUsername, String gitUserEmail, Printer printer) {
        return new BasicCommand(projectPath, os, gitUsername, gitUserEmail, printer);
    }

    /**
     * Execute git related native commands.
     *
     * @return command execution result
     */
    Result executeGit();

    /**
     * IntelliJ opening native command.
     *
     * @return command execution result
     */
    Result openIntelliJ();

    /**
     * Locate IntelliJ launcher path.
     *
     * @param launcherPathFile file where the launcher path should be stored
     * @return command execution result
     */
    Result locateIntelliJ(Path launcherPathFile);
}
