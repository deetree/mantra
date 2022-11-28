package com.github.deetree.mantra;

import com.github.deetree.mantra.config.Config;
import com.github.deetree.mantra.config.ConfigValues;
import com.github.deetree.mantra.config.Configuration;
import com.github.deetree.mantra.creator.Creator;
import com.github.deetree.mantra.oscmd.Command;
import com.github.deetree.mantra.pathresolver.PathResolver;
import com.github.deetree.mantra.pathresolver.ResolvedPaths;
import com.github.deetree.mantra.printer.Message;
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
    private String launcher;

    private Main(Arguments arguments, Printer printer, File configFile) {
        this.arguments = arguments;
        this.printer = printer;
        this.configFile = configFile;
    }

    public static void main(String[] args) {
        Main app = new Main(new Arguments(), assignPrinter(),
                new File(SystemProperty.USER_HOME.toString(), ".mantra.config"));

        final OS os = app.identifyOs();
        if (os != null) {
            Configuration configuration = new Config(app.configFile, app.createBasicConfigValues(), os, app.printer);
            app.useConfigFile(configuration);

            CommandLine cmd = new CLIParser(args, app.arguments).parse();

            if (cmd.getUnmatchedArguments().isEmpty()
                    && !app.wasHelpUsed(new UsageHelper(cmd), new VersionHelper(cmd)))
                app.checkMode(os, configuration);
        }
    }

    private static Printer assignPrinter() {
        Printer outputPrinter = Printer.getDefault();
        outputPrinter.suspendPrinting();
        return outputPrinter;
    }

    private void useConfigFile(Configuration configuration) {
        try {
            createBasicConfigIfNotExists(configuration);
            ConfigValues configValues = configuration.load();
            launcher = configValues.launcher();
            arguments.updateWithConfig(configValues);
        } catch (ActionException e) {
            printer.print(new Message(WARNING, e.getMessage()));
        }
    }

    private void createBasicConfigIfNotExists(Configuration configuration) {
        if (configuration.createConfigFile() == Result.OK)
            printer.print(new Message(SUCCESS, "Basic config file has been created"));
        else
            printer.print(new Message(INFO, "Config file has been found"));
    }

    private void checkMode(OS os, Configuration configuration) {
        if (arguments.configure) {
            printer.resumePrinting();
            useConfigMode(configuration);
        } else if (arguments.name != null) {
            if (!arguments.silentOutput)
                printer.resumePrinting();
            prepareBasicApp(os);
            if (arguments.silentOutput)
                printer.printErrors();
        }
    }

    private void prepareBasicApp(OS os) {
        ResolvedPaths paths = getResolvedPaths();
        try {
            createProject(paths);
            Command command = Command.getDefault(paths.projectPath(), os, printer);

            if (!arguments.disableGit)
                command.executeGit(arguments.gitUsername, arguments.gitEmail);
            if (!arguments.skipIdea)
                command.openIntelliJ(launcher);
        } catch (ActionException e) {
            printer.print(new Message(ERROR, e.getMessage()));
        }
    }

    private void createProject(ResolvedPaths paths) {
        printer.print(new Message(INFO, "Creating project"));
        Creator.of(paths.projectPath(), paths.javaMainFilesPath(), paths.mainResourcesPath(),
                paths.javaTestFilesPath(), paths.testResourcesPath(), arguments.groupId,
                arguments.artifactId, arguments.mainClass, arguments.javaVersion, printer).create();
        printer.print(new Message(SUCCESS, "Project created successfully"));
    }

    private ResolvedPaths getResolvedPaths() {
        printer.print(new Message(INFO, "Resolving paths"));
        ResolvedPaths paths = new PathResolver(arguments.directory, arguments.name,
                arguments.groupId, arguments.artifactId).resolvePaths();
        printer.print(new Message(SUCCESS, "Resolving paths completed successfully"));
        return paths;
    }

    private void useConfigMode(Configuration configuration) {
        try {
            if (configuration.configureDefaults() == Result.OK)
                printer.print(new Message(SUCCESS, "Config file with defaults has been created"));
            else
                printer.print(new Message(WARNING, "Something went wrong during default configuration creating"));
        } catch (ActionException e) {
            printer.print(new Message(WARNING, e.getMessage()));
        }
    }

    private ConfigValues createBasicConfigValues() {
        return new ConfigValues(arguments.directory, arguments.groupId, arguments.artifactId,
                arguments.mainClass, arguments.gitUsername, arguments.gitEmail, "");
    }

    private OS identifyOs() {
        printer.print(new Message(INFO, "Identifying operating system"));
        OS os = null;
        try {
            os = new OperatingSystem().identify();
            printer.print(new Message(SUCCESS, "Operating system identified (%s)".formatted(os.name())));
        } catch (OSNotSupportedException e) {
            printer.print(new Message(ERROR, e.getMessage()));
        }
        return os;
    }

    private boolean wasHelpUsed(Helper helper1, Helper helper2) {
        return Stream.of(helper1, helper2).anyMatch(Helper::checkHelpRequired);
    }
}
