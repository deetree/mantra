package com.github.deetree.mantra.creator;

import com.github.deetree.mantra.ActionException;
import com.github.deetree.mantra.Result;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

/**
 * Basic main class file creator.
 *
 * @author Mariusz Bal
 */
class MainClassCreator implements FileCreator {

    private final Path mainJavaFilesPath;
    private final String groupId;
    private final String artifactId;
    private final String mainClass;

    /**
     * Instantiate main class file creator.
     *
     * @param mainJavaFilesPath main java files directory path
     * @param groupId           project's groupId
     * @param artifactId        project's artifactId
     * @param mainClass         main class name
     */
    MainClassCreator(Path mainJavaFilesPath, String groupId, String artifactId, String mainClass) {
        this.mainJavaFilesPath = mainJavaFilesPath;
        this.groupId = groupId;
        this.artifactId = artifactId;
        this.mainClass = mainClass;
    }

    @Override
    public String makePreExecuteStatus() {
        return "Creating main class";
    }

    @Override
    public Result create() {
        try (InputStream pom = ResourceFileLoader.load("Main.java")) {
            new FileWriter(mainJavaFilesPath.resolve(mainClass + ".java"),
                    replaceVariables(new String(pom.readAllBytes()))).write();
            return Result.OK;
        } catch (IOException e) {
            throw new ActionException("An error occurred during main class creation");
        }
    }

    @Override
    public String makePostExecuteSuccessStatus() {
        return "Main class file created";
    }

    @Override
    public String makePostExecuteErrorStatus() {
        return "Could not create main class file";
    }

    private String replaceVariables(String pom) {
        return pom.replace("$group", groupId)
                .replace("$artifact", artifactId)
                .replace("$user", System.getProperty("user.name"))
                .replace("$main", mainClass);
    }
}
