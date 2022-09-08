package com.github.deetree.mantra.creator;

import com.github.deetree.mantra.ActionException;
import com.github.deetree.mantra.Result;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

/**
 * @author Mariusz Bal
 */
class PomCreator implements FileCreator {

    private final Path projectPath;
    private final String groupId;
    private final String artifactId;
    private final String mainClass;
    private final String javaVersion;

    PomCreator(Path projectPath, String groupId, String artifactId, String mainClass, String javaVersion) {
        this.projectPath = projectPath;
        this.groupId = groupId;
        this.artifactId = artifactId;
        this.mainClass = mainClass;
        this.javaVersion = javaVersion;
    }

    @Override
    public String preExecuteStatus() {
        return "Creating basic POM file";
    }

    @Override
    public Result create() {
        final String fileName = "pom.xml";
        try (InputStream pom = ResourceFileLoader.load(fileName)) {
            new FileWriter(projectPath.resolve(fileName),
                    replaceVariables(new String(pom.readAllBytes()))).write();
            return Result.OK;
        } catch (IOException e) {
            throw new ActionException("An error occurred during POM file creation");
        }
    }

    @Override
    public String postExecuteStatus() {
        return "Basic POM file created";
    }

    private String replaceVariables(String pom) {
        return pom.replace("$group", groupId)
                .replace("$artifact", artifactId)
                .replace("$java", javaVersion)
                .replace("$main", mainClass);
    }
}
