package com.github.deetree.mantra.creator;

import com.github.deetree.mantra.ActionException;
import com.github.deetree.mantra.Result;

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
    public String preExecuteStatus() {
        return "Creating test class";
    }

    @Override
    public Result create() {
        InputStream pom = ResourceFileLoader.load("MainTest.java");//todo check
        try {
            new FileWriter(testJavaFilesPath.resolve(mainClass + "Test.java"),
                    replaceVariables(new String(pom.readAllBytes()))).write();
            return Result.OK;
        } catch (IOException e) {
            throw new ActionException("An error occurred during main class test creation");
        }
    }

    @Override
    public String postExecuteStatus() {
        return "Test class created";
    }

    private String replaceVariables(String pom) {
        return pom.replace("$group", groupId)
                .replace("$artifact", artifactId)
                .replace("$main", mainClass);
    }
}
