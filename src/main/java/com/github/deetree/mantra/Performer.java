package com.github.deetree.mantra;

import picocli.CommandLine;

import java.nio.file.Path;

/**
 * @author Mariusz Bal
 */
class Performer {
    private final Trio parsingResult;

    Performer(Trio parsingResult) {
        this.parsingResult = parsingResult;
    }

    void execute() {
        CommandLine cmd = parsingResult.cmd();
        CommandLine.ParseResult result = parsingResult.result();
        Helper usage = new UsageHelper(cmd);
        Helper version = new VersionHelper(cmd);

        if (!wasHelpUsed(usage, version)) {
            Arguments arguments = parsingResult.arguments();
            Path projectPath = new ProjectPathResolver(arguments.directory, arguments.name).resolve();
            Path sourcesPath = new SourcePathResolver(projectPath).resolve();
            Path packagePath = new PackagePathResolver(arguments.groupId, arguments.artifactId).resolve();
            Path javaMainFilesPath = new JavaFilesPathResolver(sourcesPath, Directory.MAIN, packagePath).resolve();
            Path mainResourcesPath = new ResourcesPathResolver(sourcesPath, Directory.MAIN).resolve();
            Path javaTestFilesPath = new JavaFilesPathResolver(sourcesPath, Directory.TEST, packagePath).resolve();
            Path testResourcesPath = new ResourcesPathResolver(sourcesPath, Directory.TEST).resolve();

            try {
                createStructure(projectPath, javaMainFilesPath, mainResourcesPath,
                        javaTestFilesPath, testResourcesPath);
            } catch (ActionException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    private boolean wasHelpUsed(Helper usage, Helper version) {
        return usage.checkHelpRequired() || version.checkHelpRequired();
    }

    private void createStructure(Path projectPath, Path javaMainFilesPath, Path mainResourcesPath,
                                 Path javaTestFilesPath, Path testResourcesPath) {
        new ProjectDirectoryCreator(projectPath).create();
        new CodeDirectoryCreator(javaMainFilesPath, mainResourcesPath).create();
        new CodeDirectoryCreator(javaTestFilesPath, testResourcesPath).create();
    }
}
