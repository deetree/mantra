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

    OpenIntelliJCommand(OS os, Path projectPath, Printer printer) {
        this.os = os;
        this.projectPath = projectPath;
        this.printer = printer;
    }

    @Override
    public String makePreExecuteStatus() {
        return "Opening the project in IntelliJ IDEA";
    }

    @Override
    public Result execute() {
        if (execute(os, projectPath, findCommand().formatted(projectPath), printer) != Result.OK)
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
            case LINUX -> "$(locate idea.sh) %s > /dev/null 2>&1 &";
            case WINDOWS -> "for /f \"usebackq tokens=*\" %%i in " +
                    "(`where /R C:\\PROGRA~1 idea64.exe`) do @start \"\" \"%%i\" %s";
        };
    }
}
