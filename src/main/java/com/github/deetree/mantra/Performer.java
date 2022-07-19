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
                createGitignore(projectPath);
                createPom(projectPath, arguments.groupId, arguments.artifactId,
                        arguments.mainClass, arguments.javaVersion);
                createMain(javaMainFilesPath, arguments.groupId, arguments.artifactId, arguments.mainClass);
                createMainTest(javaTestFilesPath, arguments.groupId, arguments.artifactId, arguments.mainClass);
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

    private void createGitignore(Path projectPath) {
        new GitignoreCreator(projectPath).create();
    }

    private void createPom(Path projectPath, String groupId, String artifactId, String mainClass, int javaVersion) {
        new PomCreator(projectPath, groupId, artifactId, mainClass, String.valueOf(javaVersion)).create();
    }

    private void createMain(Path mainJavaFilesPath, String groupId, String artifactId, String mainClass) {
        new MainClassCreator(mainJavaFilesPath, groupId, artifactId, mainClass).create();
    }

    private void createMainTest(Path testJavaFilesPath, String groupId, String artifactId, String mainClass) {
        new TestClassCreator(testJavaFilesPath, groupId, artifactId, mainClass).create();
    }
}
