package com.github.deetree.mantra;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

/**
 * @author Mariusz Bal
 */
class MainClassCreator implements FileCreator {

    private final Path mainJavaFilesPath;
    private final String groupId;
    private final String artifactId;
    private final String mainClass;

    MainClassCreator(Path mainJavaFilesPath, String groupId, String artifactId, String mainClass) {
        this.mainJavaFilesPath = mainJavaFilesPath;
        this.groupId = groupId;
        this.artifactId = artifactId;
        this.mainClass = mainClass;
    }

    @Override
    public void create() {
        InputStream pom = ResourceFileLoader.load("Main.java");
        try {
            new FileWriter(mainJavaFilesPath.resolve(mainClass + ".java"),
                    replaceVariables(new String(pom.readAllBytes()))).write();
        } catch (IOException e) {
            throw new ActionException("An error occurred during main class creation");
        }
    }

    private String replaceVariables(String pom) {
        return pom.replace("$group", groupId)
                .replace("$artifact", artifactId)
                .replace("$user", System.getProperty("user.name"))
                .replace("$main", mainClass);
    }
}
