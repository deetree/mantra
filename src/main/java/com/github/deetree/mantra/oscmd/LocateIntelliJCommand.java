package com.github.deetree.mantra.oscmd;

import com.github.deetree.mantra.OS;
import com.github.deetree.mantra.Result;
import com.github.deetree.mantra.printer.Printer;

import java.nio.file.Path;

/**
 * IntelliJ IDEA launcher path locating native command.
 *
 * @author Mariusz Bal
 */
class LocateIntelliJCommand implements NativeCommand {

    private final OS os;
    private final Path launcherPathFile;
    private final Printer printer;

    /**
     * Locate IntelliJ IDEA native command.
     *
     * @param os               operating system
     * @param launcherPathFile file to store IDE launcher path
     * @param printer          output printer
     */
    LocateIntelliJCommand(OS os, Path launcherPathFile, Printer printer) {
        this.os = os;
        this.launcherPathFile = launcherPathFile;
        this.printer = printer;
    }

    @Override
    public String makePreExecuteStatus() {
        return "Locating IntelliJ IDEA";
    }

    @Override
    public Result execute() {
        return execute(os, Path.of(System.getProperty("user.home")),
                findCommand().formatted(launcherPathFile.toString()), printer);
    }

    @Override
    public String makePostExecuteSuccessStatus() {
        return "IntelliJ IDEA located. The path has been stored in the config file. You can change it in config mode.";
    }

    @Override
    public String makePostExecuteErrorStatus() {
        return "IntelliJ IDEA could not be located. Provide the path manually.";
    }

    private String findCommand() {
        return switch (os) {
            case LINUX -> "locate idea.sh > %s";
            case WINDOWS -> "where /R C:\\PROGRA~1 idea64.exe > %s";
        };
    }
}
