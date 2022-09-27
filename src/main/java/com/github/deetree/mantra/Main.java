package com.github.deetree.mantra;

import com.github.deetree.mantra.config.Config;
import com.github.deetree.mantra.config.ConfigValues;
import com.github.deetree.mantra.config.Configuration;
import com.github.deetree.mantra.creator.Creator;
import com.github.deetree.mantra.oscmd.Command;
import com.github.deetree.mantra.pathresolver.PathResolver;
import com.github.deetree.mantra.pathresolver.ResolvedPaths;
import com.github.deetree.mantra.printer.Printer;
import picocli.CommandLine;

import java.io.File;
import java.util.stream.Stream;

import static com.github.deetree.mantra.printer.Level.*;

/**
 * @author Mariusz Bal
 */
class Main {
    public static void main(String[] args) {
        Printer printer = Printer.getDefault();

        printer.print(INFO, "Identifying operating system");
        OS os;
        try {
            os = new OperatingSystem().identify();
            printer.print(SUCCESS, "Operating system identified (%s)".formatted(os.name()));
        } catch (OSNotSupportedException e) {
            printer.print(ERROR, e.getMessage());
            return;//todo exit app
        }

        File configFile = new File(System.getProperty("user.home"), ".mantra.config");
        Arguments arguments = new Arguments();

        ConfigValues configValues = new ConfigValues(arguments.directory, arguments.groupId, arguments.artifactId,
                arguments.mainClass, arguments.gitUsername, arguments.gitEmail, "");
        Configuration configuration = new Config(configFile, configValues, os, printer);

        try {
            if (configuration.createConfigFile() == Result.OK)
                printer.print(SUCCESS, "Basic config file has been created");
            else
                printer.print(INFO, "Config file has been found");
            configValues = configuration.load();
            arguments.updateWithConfig(configValues);
        } catch (ActionException e) {
            printer.print(WARNING, e.getMessage());
        }

        CommandLine cmd = new CLIParser(args, arguments).parse();

        Helper usage = new UsageHelper(cmd);
        Helper version = new VersionHelper(cmd);

        if (!wasHelpUsed(usage, version)) {
            if (arguments.configure) {
                try {
                    if (configuration.configureDefaults() == Result.OK)
                        printer.print(SUCCESS, "Config file with defaults has been created");
                    else
                        printer.print(WARNING, "Something went wrong during default configuration creating");
                } catch (ActionException e) {
                    printer.print(WARNING, e.getMessage());
                }
            } else if (arguments.name != null) {
                printer.print(INFO, "Resolving paths");
                ResolvedPaths paths = new PathResolver(arguments.directory, arguments.name,
                        arguments.groupId, arguments.artifactId).resolvePaths();
                printer.print(SUCCESS, "Resolving paths completed successfully");

                try {
                    printer.print(INFO, "Creating project");
                    Creator.of(paths.projectPath(), paths.javaMainFilesPath(), paths.mainResourcesPath(),
                            paths.javaTestFilesPath(), paths.testResourcesPath(), arguments.groupId,
                            arguments.artifactId, arguments.mainClass, arguments.javaVersion, printer).create();
                    printer.print(SUCCESS, "Project created successfully");
                    Command command = Command.getDefault(paths.projectPath(), os,
                            arguments.gitUsername, arguments.gitEmail, printer);
                    if (!arguments.disableGit)
                        command.executeGit();
                    command.openIntelliJ();
                } catch (ActionException e) {
                    printer.print(ERROR, e.getMessage());
                }
            }
        }
    }

    private static boolean wasHelpUsed(Helper helper1, Helper helper2) {
        return Stream.of(helper1, helper2).anyMatch(Helper::checkHelpRequired);
    }
}
