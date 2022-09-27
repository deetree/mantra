package com.github.deetree.mantra.oscmd;

import com.github.deetree.mantra.OS;
import com.github.deetree.mantra.Result;
import com.github.deetree.mantra.printer.Printer;

import java.nio.file.Path;

/**
 * @author Mariusz Bal
 */
public interface Command {

    static Command getDefault(Path projectPath, OS os, String gitUsername, String gitUserEmail, Printer printer) {
        return new BasicCommand(projectPath, os, gitUsername, gitUserEmail, printer);
    }

    Result executeGit();

    Result openIntelliJ();

    Result locateIntelliJ(Path launcherPathFile);

}
