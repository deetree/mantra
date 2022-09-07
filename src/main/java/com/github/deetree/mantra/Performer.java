package com.github.deetree.mantra;

import com.github.deetree.mantra.creator.Creator;
import com.github.deetree.mantra.oscmd.Command;
import com.github.deetree.mantra.pathresolver.PathResolver;
import com.github.deetree.mantra.pathresolver.ResolvedPaths;
import com.github.deetree.mantra.printer.Level;
import com.github.deetree.mantra.printer.Printer;
import picocli.CommandLine;

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
            ResolvedPaths paths = new PathResolver(arguments.directory, arguments.name,
                    arguments.groupId, arguments.artifactId).resolvePaths();
            printer.print(Level.SUCCESS, "Resolving paths completed successfully");

            try {
                printer.print(Level.INFO, "Creating project");
                Creator.of(paths.projectPath(), paths.javaMainFilesPath(), paths.mainResourcesPath(),
                        paths.javaTestFilesPath(), paths.testResourcesPath(), arguments.groupId,
                        arguments.artifactId, arguments.mainClass, arguments.javaVersion).create();
                printer.print(Level.SUCCESS, "Project created successfully");
                printer.print(Level.INFO, "Identifying operating system");
                OS os = new OperatingSystem().identify();
                printer.print(Level.SUCCESS, "Operating system identified (%s)".formatted(os.name()));
                Command command = Command.getDefault(paths.projectPath(), os,
                        arguments.gitUsername, arguments.gitEmail);
                if (!arguments.disableGit)
                    command.executeGit();
                command.openIntelliJ();
            } catch (ActionException | OSNotSupportedException e) {
                printer.print(Level.ERROR, e.getMessage());
            }
        }
    }

    private boolean wasHelpUsed(Helper usage, Helper version) {
        return usage.checkHelpRequired() || version.checkHelpRequired();
    }
}
