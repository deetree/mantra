package com.github.deetree.mantra.oscmd;

import com.github.deetree.mantra.OS;
import com.github.deetree.mantra.Result;
import com.github.deetree.mantra.printer.Printer;

import java.nio.file.Path;

/**
 * @author Mariusz Bal
 */
class LocateIntelliJCommand implements NativeCommand {

    private final OS os;
    private final Path launcherPathFile;
    private final Printer printer;

    LocateIntelliJCommand(OS os, Path launcherPathFile, Printer printer) {
        this.os = os;
        this.launcherPathFile = launcherPathFile;
        this.printer = printer;
    }

    @Override
    public String preExecuteStatus() {
        return "Locating IntelliJ IDEA";
    }

    @Override
    public Result execute() {
        return execute(os, Path.of(System.getProperty("user.home")),
                findCommand().formatted(launcherPathFile.toString()), printer);
    }

    @Override
    public String postExecuteStatus() {
        return "IntelliJ IDEA located. The path has been stored in the config file. You can change it in config mode.";
    }

    private String findCommand() {
        return switch (os) {
            case LINUX -> "locate idea.sh > %s";
            case WINDOWS -> "where /R C:\\PROGRA~1 idea64.exe > %s";
        };
    }
}
