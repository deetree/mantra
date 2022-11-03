package com.github.deetree.mantra;

import com.github.deetree.mantra.config.ConfigValues;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.util.Map;
import java.util.Stack;

/**
 * Mantra's command line options.
 *
 * @author Mariusz Bal
 */
@Command(name = "mantra", description = "Kickstart your Java (Maven) project",
        showDefaultValues = true, abbreviateSynopsis = true,
        mixinStandardHelpOptions = true, versionProvider = VersionProvider.class)
class Arguments implements Runnable {

    @Parameters(description = "Project's name")
    String name;

    @Option(names = {"--directory", "-d"}, description = "Directory where the project will be created")
    String directory = System.getProperty("user.home");

    @Option(names = {"--group", "-g"}, description = "Project's groupId")
    String groupId = "com.example";

    @Option(names = {"--artifact", "-a"}, description = "Project's artifactId (default: project's name)")
    String artifactId;

    @Option(names = {"--main-class", "-m"}, description = "Main class name")
    String mainClass = "Main";

    @Option(names = {"--java", "-j"}, description = "Java version")
    int javaVersion = 17;

    @Option(names = {"--no-git", "-n"}, description = "Disable git initialization")
    boolean disableGit;

    @Option(names = {"--git-username", "-u"}, description = "Set local git username")
    String gitUsername;

    @Option(names = {"--git-email", "-e"}, description = "Set local git email")
    String gitEmail;

    @Option(names = {"--no-idea", "-l"}, description = "Do not launch IntelliJ IDEA")
    boolean skipIdea;

    @Option(names = {"--configure", "-c"}, description = "Create global defaults config file",
            preprocessor = ConfigFlagPreprocessor.class)
    boolean configure;

    @Override
    public void run() {
        if (artifactId == null)
            artifactId = name;
    }

    /**
     * Update options with defaults read from config file.
     *
     * @param configValues config file defaults
     */
    void updateWithConfig(ConfigValues configValues) {
        directory = configValues.directory();
        groupId = configValues.group();
        artifactId = configValues.artifact();
        mainClass = configValues.main();
        gitUsername = configValues.username();
        gitEmail = configValues.email();
    }

    /**
     * Configuration flag preprocessor which adds mocked project name to command line arguments
     * when configuration flag was matched, so the error informing about no required parameter (project name)
     * does not appear and the unnecessary usage help is not printed out.
     */
    static class ConfigFlagPreprocessor implements CommandLine.IParameterPreprocessor {
        @Override
        public boolean preprocess(Stack<String> args, CommandLine.Model.CommandSpec commandSpec,
                                  CommandLine.Model.ArgSpec argSpec, Map<String, Object> info) {
            args.push("configModeHelpNotNeeded");
            return false;
        }
    }
}
