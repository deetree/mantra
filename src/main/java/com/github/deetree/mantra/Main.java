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
final class Main {

    private final Printer printer;
    private final Arguments arguments;
    private final File configFile;

    private Main(Arguments arguments, Printer printer, File configFile) {
        this.arguments = arguments;
        this.printer = printer;
        this.configFile = configFile;
    }

    public static void main(String[] args) {
        Main app = new Main(new Arguments(), Printer.getDefault(),
                new File(System.getProperty("user.home"), ".mantra.config"));

        final OS os = app.identifyOs();
        if (os != null) {
            Configuration configuration = new Config(app.configFile, app.createBasicConfigValues(), os, app.printer);
            app.useConfigFile(configuration);

            CommandLine cmd = new CLIParser(args, app.arguments).parse();

            if (!app.wasHelpUsed(new UsageHelper(cmd), new VersionHelper(cmd)))
                app.checkMode(os, configuration);
        }
    }

    private void useConfigFile(Configuration configuration) {
        try {
            createBasicConfigIfNotExists(configuration);
            ConfigValues configValues = configuration.load();
            arguments.updateWithConfig(configValues);
        } catch (ActionException e) {
            printer.print(WARNING, e.getMessage());
        }
    }

    private void createBasicConfigIfNotExists(Configuration configuration) {
        if (configuration.createConfigFile() == Result.OK)
            printer.print(SUCCESS, "Basic config file has been created");
        else
            printer.print(INFO, "Config file has been found");
    }

    private void checkMode(OS os, Configuration configuration) {
        if (arguments.configure)
            useConfigMode(configuration);
        else if (arguments.name != null)
            prepareBasicApp(os);
    }

    private void prepareBasicApp(OS os) {
        ResolvedPaths paths = getResolvedPaths();

        try {
            createProject(paths);
            Command command = Command.getDefault(paths.projectPath(), os,
                    arguments.gitUsername, arguments.gitEmail, printer);

            if (!arguments.disableGit)
                command.executeGit();
            if (!arguments.skipIdea)
                command.openIntelliJ();
        } catch (ActionException e) {
            printer.print(ERROR, e.getMessage());
        }
    }

    private void createProject(ResolvedPaths paths) {
        printer.print(INFO, "Creating project");
        Creator.of(paths.projectPath(), paths.javaMainFilesPath(), paths.mainResourcesPath(),
                paths.javaTestFilesPath(), paths.testResourcesPath(), arguments.groupId,
                arguments.artifactId, arguments.mainClass, arguments.javaVersion, printer).create();
        printer.print(SUCCESS, "Project created successfully");
    }

    private ResolvedPaths getResolvedPaths() {
        printer.print(INFO, "Resolving paths");
        ResolvedPaths paths = new PathResolver(arguments.directory, arguments.name,
                arguments.groupId, arguments.artifactId).resolvePaths();
        printer.print(SUCCESS, "Resolving paths completed successfully");
        return paths;
    }

    private void useConfigMode(Configuration configuration) {
        try {
            if (configuration.configureDefaults() == Result.OK)
                printer.print(SUCCESS, "Config file with defaults has been created");
            else
                printer.print(WARNING, "Something went wrong during default configuration creating");
        } catch (ActionException e) {
            printer.print(WARNING, e.getMessage());
        }
    }

    private ConfigValues createBasicConfigValues() {
        return new ConfigValues(arguments.directory, arguments.groupId, arguments.artifactId,
                arguments.mainClass, arguments.gitUsername, arguments.gitEmail, "");
    }

    private OS identifyOs() {
        printer.print(INFO, "Identifying operating system");
        OS os = null;
        try {
            os = new OperatingSystem().identify();
            printer.print(SUCCESS, "Operating system identified (%s)".formatted(os.name()));
        } catch (OSNotSupportedException e) {
            printer.print(ERROR, e.getMessage());
        }
        return os;
    }

    private boolean wasHelpUsed(Helper helper1, Helper helper2) {
        return Stream.of(helper1, helper2).anyMatch(Helper::checkHelpRequired);
    }
}
