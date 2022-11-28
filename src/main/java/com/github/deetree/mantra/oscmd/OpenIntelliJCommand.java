package com.github.deetree.mantra.oscmd;

import com.github.deetree.mantra.ActionException;
import com.github.deetree.mantra.OS;
import com.github.deetree.mantra.Result;
import com.github.deetree.mantra.printer.Printer;

import java.nio.file.Path;

/**
 * IntelliJ IDEA launcher.
 *
 * @author Mariusz Bal
 */
class OpenIntelliJCommand implements NativeCommand {

    private final OS os;
    private final Path projectPath;
    private final Printer printer;
    private final String launcher;

    /**
     * IntelliJ IDEA opener.
     *
     * @param os          operating system
     * @param projectPath project directory path
     * @param printer     output printer
     * @param launcher    IDE launcher path string
     */
    OpenIntelliJCommand(OS os, Path projectPath, Printer printer, String launcher) {

        this.os = os;
        this.projectPath = projectPath;
        this.printer = printer;
        this.launcher = launcher;
    }

    @Override
    public String makePreExecuteStatus() {
        return "Opening the project in IntelliJ IDEA";
    }

    @Override
    public Result execute() {
        if (execute(os, projectPath, findCommand().formatted(substituteLauncher(), projectPath), printer) != Result.OK)
            throw new ActionException("An exception occurred during IntelliJ IDEA opening");
        return Result.OK;
    }

    @Override
    public String makePostExecuteSuccessStatus() {
        return "Project opened successfully";
    }

    @Override
    public String makePostExecuteErrorStatus() {
        return "The project could not be opened";
    }

    private String findCommand() {
        return switch (os) {
            case LINUX -> "%s %s > /dev/null 2>&1 &";
            case WINDOWS -> "for /f \"usebackq tokens=*\" %%i in " +
                    "%s do @start \"\" \"%%i\" %s";
        };
    }

    private String substituteLauncher() {
        return launcher.isBlank() ? switch (os) {
            case LINUX -> "$(locate idea.sh)";
            case WINDOWS -> "(`where /R C:\\PROGRA~1 idea64.exe`)";
        } : launcher;
    }
}
