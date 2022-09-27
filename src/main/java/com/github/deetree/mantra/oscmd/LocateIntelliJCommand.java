package com.github.deetree.mantra.oscmd;

import com.github.deetree.mantra.OS;
import com.github.deetree.mantra.Result;

import java.nio.file.Path;

/**
 * @author Mariusz Bal
 */
class LocateIntelliJCommand implements NativeCommand {

    private final OS os;
    private final Path launcherPathFile;

    LocateIntelliJCommand(OS os, Path launcherPathFile) {
        this.os = os;
        this.launcherPathFile = launcherPathFile;
    }

    @Override
    public String preExecuteStatus() {
        return "Locating IntelliJ IDEA";
    }

    @Override
    public Result execute() {
        return execute(os, Path.of(System.getProperty("user.home")),
                findCommand().formatted(launcherPathFile.toString()));
    }

    @Override
    public String postExecuteStatus() {
        return "IntelliJ IDEA located. The path has been stored in the config file. You can change it below.";
    }

    private String findCommand() {
        return switch (os) {
            case LINUX -> "locate idea.sh > %s";
            case WINDOWS -> "where /R C:\\PROGRA~1 idea64.exe > %s";
        };
    }
}
