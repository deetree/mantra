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
            Printer printer = new ConsolePrinter();
            printer.print(Level.INFO, "Resolving paths");
            Path projectPath = new ProjectPathResolver(arguments.directory, arguments.name).resolve();
            Path sourcesPath = new SourcePathResolver(projectPath).resolve();
            Path packagePath = new PackagePathResolver(arguments.groupId, arguments.artifactId).resolve();
            Path javaMainFilesPath = new JavaFilesPathResolver(sourcesPath, Directory.MAIN, packagePath).resolve();
            Path mainResourcesPath = new ResourcesPathResolver(sourcesPath, Directory.MAIN).resolve();
            Path javaTestFilesPath = new JavaFilesPathResolver(sourcesPath, Directory.TEST, packagePath).resolve();
            Path testResourcesPath = new ResourcesPathResolver(sourcesPath, Directory.TEST).resolve();
            printer.print(Level.SUCCESS, "Resolving paths completed successfully");

            try {
                printer.print(Level.INFO, "Creating directories structure");
                createStructure(projectPath, javaMainFilesPath, mainResourcesPath,
                        javaTestFilesPath, testResourcesPath);
                printer.print(Level.SUCCESS, "Directories structure created successfully");
                printer.print(Level.INFO, "Creating basic .gitignore file");
                createGitignore(projectPath);
                printer.print(Level.SUCCESS, "Basic .gitignore file created");
                printer.print(Level.INFO, "Creating basic POM file");
                createPom(projectPath, arguments.groupId, arguments.artifactId,
                        arguments.mainClass, arguments.javaVersion);
                printer.print(Level.SUCCESS, "Basic POM file created");
                printer.print(Level.INFO, "Creating main class");
                createMain(javaMainFilesPath, arguments.groupId, arguments.artifactId, arguments.mainClass);
                printer.print(Level.SUCCESS, "Main class created");
                printer.print(Level.INFO, "Creating test class");
                createMainTest(javaTestFilesPath, arguments.groupId, arguments.artifactId, arguments.mainClass);
                printer.print(Level.SUCCESS, "Test class created");
                printer.print(Level.INFO, "Identifying operating system");
                OS os = new OperatingSystem().identify();
                printer.print(Level.SUCCESS, "Operating system identified (%s)".formatted(os.name()));
                if (!arguments.disableGit) {
                    printer.print(Level.INFO, "Initializing local git repository");
                    initializeGitRepo(projectPath, os);
                    printer.print(Level.SUCCESS, "Git repository initialized successfully");
                    printer.print(Level.INFO, "Configuring git repository");
                    configureGitUserInfo(projectPath, os, arguments.gitUsername, arguments.gitEmail);
                    printer.print(Level.SUCCESS, "Git repository configured");
                    printer.print(Level.INFO, "Creating initial commit");
                    createInitCommit(projectPath, os);
                    printer.print(Level.SUCCESS, "Initial commit created");
                }
            } catch (ActionException e) {
                printer.print(Level.ERROR, e.getMessage());
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

    private void initializeGitRepo(Path projectPath, OS os) {
        new InitializeGitCommand(os, projectPath).execute();
    }

    private void configureGitUserInfo(Path projectPath, OS os, String gitUsername, String gitUserEmail) {
        new LocalGitUserConfigCommand(os, projectPath, gitUsername, gitUserEmail).execute();
    }

    private void createInitCommit(Path projectPath, OS os) {
        new CreateInitCommitCommand(os, projectPath).execute();
    }
}
