package com.github.deetree.mantra.creator;

import com.github.deetree.mantra.Result;
import com.github.deetree.mantra.Status;
import com.github.deetree.mantra.printer.Level;
import com.github.deetree.mantra.printer.Message;
import com.github.deetree.mantra.printer.Printer;

import java.nio.file.Path;
import java.util.stream.Stream;

/**
 * Basic project structure creator.
 *
 * @author Mariusz Bal
 */
class BasicCreator implements Creator {

    private final Path projectPath;
    private final Path javaMainFilesPath;
    private final Path mainResourcesPath;
    private final Path javaTestFilesPath;
    private final Path testResourcesPath;
    private final String groupId;
    private final String artifactId;
    private final String mainClass;
    private final int javaVersion;
    private final Printer printer;

    /**
     * Instantiate basic creator.
     *
     * @param projectPath       project directory path
     * @param javaMainFilesPath java main files directory path
     * @param mainResourcesPath main resources directory path
     * @param javaTestFilesPath java test files directory path
     * @param testResourcesPath test resources directory path
     * @param groupId           project's groupId
     * @param artifactId        project's artifactId
     * @param mainClass         main class name
     * @param javaVersion       java version
     * @param printer           output printer
     */
    BasicCreator(Path projectPath, Path javaMainFilesPath, Path mainResourcesPath,
                 Path javaTestFilesPath, Path testResourcesPath, String groupId,
                 String artifactId, String mainClass, int javaVersion, Printer printer) {
        this.projectPath = projectPath;
        this.javaMainFilesPath = javaMainFilesPath;
        this.mainResourcesPath = mainResourcesPath;
        this.javaTestFilesPath = javaTestFilesPath;
        this.testResourcesPath = testResourcesPath;
        this.groupId = groupId;
        this.artifactId = artifactId;
        this.mainClass = mainClass;
        this.javaVersion = javaVersion;
        this.printer = printer;
    }

    @Override
    public Result create() {
        createDirectoryStructure();
        createBasicFiles();
        return Result.OK;
    }

    private void createDirectoryStructure() {
        Stream.of(
                new ProjectDirectoryCreator(projectPath),
                new CodeDirectoryCreator(javaMainFilesPath, mainResourcesPath),
                new CodeDirectoryCreator(javaTestFilesPath, testResourcesPath)
        ).forEach(this::executeCreator);
    }

    private void createBasicFiles() {
        Stream.of(
                new GitignoreCreator(projectPath),
                new PomCreator(projectPath, groupId, artifactId, mainClass, String.valueOf(javaVersion)),
                new MainClassCreator(javaMainFilesPath, groupId, artifactId, mainClass),
                new TestClassCreator(javaTestFilesPath, groupId, artifactId, mainClass)
        ).forEach(this::executeCreator);
    }

    private void executeCreator(Creator creator) {
        Status status = (Status) creator;
        printer.print(new Message(Level.INFO, status.makePreExecuteStatus()));
        if (creator.create() == Result.OK)
            printer.print(new Message(Level.SUCCESS, status.makePostExecuteSuccessStatus()));
        else
            printer.print(new Message(Level.ERROR, status.makePostExecuteErrorStatus()));
    }
}
