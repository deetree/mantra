package com.github.deetree.mantra;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

/**
 * @author Mariusz Bal
 */
class TestClassCreator implements FileCreator {

    private final Path testJavaFilesPath;
    private final String groupId;
    private final String artifactId;
    private final String mainClass;

    TestClassCreator(Path testJavaFilesPath, String groupId, String artifactId, String mainClass) {
        this.testJavaFilesPath = testJavaFilesPath;
        this.groupId = groupId;
        this.artifactId = artifactId;
        this.mainClass = mainClass;
    }

    @Override
    public void create() {
        InputStream pom = ResourceFileLoader.load("MainTest.java");
        try {
            new FileWriter(testJavaFilesPath.resolve(mainClass + "Test.java"),
                    replaceVariables(new String(pom.readAllBytes()))).write();
        } catch (IOException e) {
            throw new ActionException("An error occurred during main class test creation");
        }
    }

    private String replaceVariables(String pom) {
        return pom.replace("$group", groupId)
                .replace("$artifact", artifactId)
                .replace("$main", mainClass);
    }
}
