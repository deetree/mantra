package com.github.deetree.mantra;

import com.github.deetree.mantra.creator.Creator;
import com.github.deetree.mantra.printer.Level;
import com.github.deetree.mantra.printer.Printer;
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
        Arguments arguments = parsingResult.arguments();

        if (!wasHelpUsed(usage, version) && arguments.name != null) {
            Printer printer = Printer.getDefault();
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
                printer.print(Level.INFO, "Creating project");
                createProject(projectPath, javaMainFilesPath, mainResourcesPath,
                        javaTestFilesPath, testResourcesPath, arguments.groupId,
                        arguments.artifactId, arguments.mainClass, arguments.javaVersion);
                printer.print(Level.SUCCESS, "Project created successfully");
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
                printer.print(Level.INFO, "Opening the project in IntelliJ IDEA");
                openIntelliJ(projectPath, os);
                printer.print(Level.SUCCESS, "Project opened successfully");
            } catch (ActionException | OSNotSupportedException e) {
                printer.print(Level.ERROR, e.getMessage());
            }
        }
    }

    private boolean wasHelpUsed(Helper usage, Helper version) {
        return usage.checkHelpRequired() || version.checkHelpRequired();
    }

    private void createProject(Path projectPath, Path javaMainFilesPath, Path mainResourcesPath,
                               Path javaTestFilesPath, Path testResourcesPath, String groupId,
                               String artifactId, String mainClass, int javaVersion) {
        Creator.of(projectPath, javaMainFilesPath, mainResourcesPath, javaTestFilesPath, testResourcesPath,
                groupId, artifactId, mainClass, javaVersion).create();
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

    private void openIntelliJ(Path projectPath, OS os) {
        new OpenIntelliJCommand(os, projectPath).execute();
    }
}
