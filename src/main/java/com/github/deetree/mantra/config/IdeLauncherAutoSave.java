package com.github.deetree.mantra.config;

import com.github.deetree.mantra.OS;
import com.github.deetree.mantra.oscmd.Command;
import com.github.deetree.mantra.printer.Printer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Find the IDE launcher path automatically.
 *
 * @author Mariusz Bal
 */
class IdeLauncherAutoSave {

    private final OS os;
    private final Printer printer;

    /**
     * Create IDE launcher path auto save object.
     *
     * @param os      operating system
     * @param printer printer for output printing
     */
    IdeLauncherAutoSave(OS os, Printer printer) {
        this.os = os;
        this.printer = printer;
    }

    /**
     * Find the IDE launcher path.
     *
     * @return IDE launcher path that has been found
     */
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
