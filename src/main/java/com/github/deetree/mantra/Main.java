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

        File configFile = new File(System.getProperty("user.home"), ".mantra.config");
        Arguments arguments = new Arguments();

        ConfigValues configValues = new ConfigValues(arguments.directory, arguments.groupId, arguments.artifactId,
                arguments.mainClass, arguments.gitUsername, arguments.gitEmail, "");
        Configuration configuration = new Config(configFile, configValues);

        try {
            if (configuration.createConfigFile() == Result.OK)
                printer.print(INFO, "Config file has been created");
            else
                printer.print(INFO, "Config file has been found. Creating new one is being skipped.");
            configValues = configuration.load();
            arguments.updateWithConfig(configValues);
        } catch (ActionException e) {
            printer.print(WARNING, e.getMessage());
        }

        Trio parsingResult = new CLIParser(args, arguments).parse();

        CommandLine cmd = parsingResult.cmd();
        arguments = parsingResult.arguments();

        System.out.println(arguments);

        Helper usage = new UsageHelper(cmd);
        Helper version = new VersionHelper(cmd);
        if (false) {
            //if (!wasHelpUsed(usage, version) && arguments.name != null) {
            printer.print(INFO, "Resolving paths");
            ResolvedPaths paths = new PathResolver(arguments.directory, arguments.name,
                    arguments.groupId, arguments.artifactId).resolvePaths();
            printer.print(SUCCESS, "Resolving paths completed successfully");

            try {
                printer.print(INFO, "Creating project");
                Creator.of(paths.projectPath(), paths.javaMainFilesPath(), paths.mainResourcesPath(),
                        paths.javaTestFilesPath(), paths.testResourcesPath(), arguments.groupId,
                        arguments.artifactId, arguments.mainClass, arguments.javaVersion).create();
                printer.print(SUCCESS, "Project created successfully");
                printer.print(INFO, "Identifying operating system");
                OS os = new OperatingSystem().identify();
                printer.print(SUCCESS, "Operating system identified (%s)".formatted(os.name()));
                Command command = Command.getDefault(paths.projectPath(), os,
                        arguments.gitUsername, arguments.gitEmail);
                if (!arguments.disableGit)
                    command.executeGit();
                command.openIntelliJ();
            } catch (ActionException | OSNotSupportedException e) {
                printer.print(ERROR, e.getMessage());
            }
        }
    }

    private static boolean wasHelpUsed(Helper helper1, Helper helper2) {
        return Stream.of(helper1, helper2).allMatch(Helper::checkHelpRequired);
    }
}
