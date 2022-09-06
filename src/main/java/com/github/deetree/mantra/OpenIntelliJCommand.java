package com.github.deetree.mantra;

import java.nio.file.Path;

/**
 * @author Mariusz Bal
 */
class OpenIntelliJCommand implements NativeCommand {

    private final OS os;
    private final Path projectPath;

    OpenIntelliJCommand(OS os, Path projectPath) {
        this.os = os;
        this.projectPath = projectPath;
    }

    @Override
    public Result execute() {
        if (execute(os, projectPath, findCommand().formatted(projectPath)) != Result.OK)
            throw new ActionException("An exception occurred during IntelliJ IDEA opening");
        return Result.OK;
    }

    private String findCommand() {
        return switch (os) {
            case LINUX -> "$(locate idea.sh) %s > /dev/null 2>&1 &";
            case WINDOWS -> "for /f \"usebackq tokens=*\" %%i in " +
                    "(`where /R C:\\PROGRA~1 idea64.exe`) do @start \"\" \"%%i\" %s";
        };
    }
}
