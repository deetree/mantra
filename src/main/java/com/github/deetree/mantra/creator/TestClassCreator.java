package com.github.deetree.mantra.creator;

import com.github.deetree.mantra.ActionException;
import com.github.deetree.mantra.Result;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

/**
 * Basic test class file creator.
 *
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
    public String makePreExecuteStatus() {
        return "Creating test class";
    }

    @Override
    public Result create() {
        try (InputStream pom = ResourceFileLoader.load("MainTest.java")) {
            new FileWriter(testJavaFilesPath.resolve(mainClass + "Test.java"),
                    replaceVariables(new String(pom.readAllBytes()))).write();
            return Result.OK;
        } catch (IOException e) {
            throw new ActionException("An error occurred during main class test creation");
        }
    }

    @Override
    public String makePostExecuteSuccessStatus() {
        return "Test class file created";
    }

    @Override
    public String makePostExecuteErrorStatus() {
        return "Could not create test class file";
    }

    private String replaceVariables(String pom) {
        return pom.replace("$group", groupId)
                .replace("$artifact", artifactId)
                .replace("$main", mainClass);
    }
}
