package com.github.deetree.mantra.creator;

import com.github.deetree.mantra.Result;

import java.nio.file.Path;

/**
 * @author Mariusz Bal
 */
@FunctionalInterface
public interface Creator {

    Result create();

    static Creator of(Path projectPath, Path javaMainFilesPath, Path mainResourcesPath,
                      Path javaTestFilesPath, Path testResourcesPath, String groupId,
                      String artifactId, String mainClass, int javaVersion) {
        return new BasicCreator(projectPath, javaMainFilesPath, mainResourcesPath,
                javaTestFilesPath, testResourcesPath, groupId, artifactId, mainClass, javaVersion);
    }
}
