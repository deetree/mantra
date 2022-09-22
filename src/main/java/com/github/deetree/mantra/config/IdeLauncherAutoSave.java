package com.github.deetree.mantra.config;

import com.github.deetree.mantra.OS;
import com.github.deetree.mantra.oscmd.Command;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

/**
 * @author Mariusz Bal
 */
class IdeLauncherAutoSave {

    private final OS os;

    IdeLauncherAutoSave(OS os) {
        this.os = os;
    }

    String findPath() {
        try {
            Path launcherPathFile = Path.of(System.getProperty("java.io.tmpdir"), "idea.path");
            Command.getDefault(Path.of(""), os, "", "").locateIntelliJ(launcherPathFile);
            return Files.readString(launcherPathFile).strip();
        } catch (IOException e) {
            return "";
        }
    }
}
