package com.github.deetree.mantra.creator;

import com.github.deetree.mantra.Result;
import com.github.deetree.mantra.printer.Printer;

import java.nio.file.Path;

/**
 * @author Mariusz Bal
 */
@FunctionalInterface
public interface Creator {

    /**
     * Static factory method for instantiating {@link BasicCreator}
     *
     * @param projectPath       project path
     * @param javaMainFilesPath java main files path
     * @param mainResourcesPath main resources path
     * @param javaTestFilesPath java test files path
     * @param testResourcesPath test resources path
     * @param groupId           project's groupId
     * @param artifactId        project's artifactId
     * @param mainClass         main class name
     * @param javaVersion       java version
     * @param printer           printer
     * @return creator for creating project structure
     */
    static Creator of(Path projectPath, Path javaMainFilesPath, Path mainResourcesPath,
                      Path javaTestFilesPath, Path testResourcesPath, String groupId,
                      String artifactId, String mainClass, int javaVersion, Printer printer) {
        return new BasicCreator(projectPath, javaMainFilesPath, mainResourcesPath,
                javaTestFilesPath, testResourcesPath, groupId, artifactId, mainClass, javaVersion, printer);
    }

    /**
     * Create project structure
     *
     * @return result of structure creating
     */
    Result create();
}
