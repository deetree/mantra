package com.github.deetree.mantra.oscmd;

import com.github.deetree.mantra.OS;
import com.github.deetree.mantra.Result;

import java.nio.file.Path;
import java.util.stream.Stream;

/**
 * @author Mariusz Bal
 */
class BasicCommand implements Command {

    private final Path projectPath;
    private final OS os;
    private final String gitUsername;
    private final String gitUserEmail;

    BasicCommand(Path projectPath, OS os, String gitUsername, String gitUserEmail) {
        this.projectPath = projectPath;
        this.os = os;
        this.gitUsername = gitUsername;
        this.gitUserEmail = gitUserEmail;
    }

    @Override
    public Result executeGit() {
        Stream.of(
                new InitializeGitCommand(os, projectPath),
                new LocalGitUserConfigCommand(os, projectPath, gitUsername, gitUserEmail),
                new CreateInitCommitCommand(os, projectPath)
        ).forEach(NativeCommand::execute);
        return Result.OK;
    }

    @Override
    public Result openIntelliJ() {
        return new OpenIntelliJCommand(os, projectPath).execute();
    }
}
