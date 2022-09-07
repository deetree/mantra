package com.github.deetree.mantra.oscmd;

import com.github.deetree.mantra.OS;
import com.github.deetree.mantra.Result;

import java.nio.file.Path;

/**
 * @author Mariusz Bal
 */
public interface Command {

    static Command getDefault(Path projectPath, OS os, String gitUsername, String gitUserEmail) {
        return new BasicCommand(projectPath, os, gitUsername, gitUserEmail);
    }

    Result executeGit();

    Result openIntelliJ();

}
