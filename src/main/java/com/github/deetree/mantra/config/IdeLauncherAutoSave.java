package com.github.deetree.mantra.config;

import com.github.deetree.mantra.OS;
import com.github.deetree.mantra.oscmd.Command;
import com.github.deetree.mantra.printer.Printer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author Mariusz Bal
 */
class IdeLauncherAutoSave {

    private final OS os;
    private final Printer printer;

    IdeLauncherAutoSave(OS os, Printer printer) {
        this.os = os;
        this.printer = printer;
    }

    String findPath() {
        try {
            Path launcherPathFile = Files.createTempFile("idea_launcher", ".path");
            Command.getDefault(Path.of(""), os, "", "", printer)
                    .locateIntelliJ(launcherPathFile);
            return Files.readString(launcherPathFile).strip();
        } catch (IOException e) {
            return "";
        }
    }
}
