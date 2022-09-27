package com.github.deetree.mantra.creator;

import com.github.deetree.mantra.Result;
import com.github.deetree.mantra.printer.Printer;

import java.nio.file.Path;

/**
 * @author Mariusz Bal
 */
@FunctionalInterface
public interface Creator {

    static Creator of(Path projectPath, Path javaMainFilesPath, Path mainResourcesPath,
                      Path javaTestFilesPath, Path testResourcesPath, String groupId,
                      String artifactId, String mainClass, int javaVersion, Printer printer) {
        return new BasicCreator(projectPath, javaMainFilesPath, mainResourcesPath,
                javaTestFilesPath, testResourcesPath, groupId, artifactId, mainClass, javaVersion, printer);
    }

    Result create();
}
