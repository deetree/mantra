package com.github.deetree.mantra;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

/**
 * @author Mariusz Bal
 */
@Command(name = "mantra", description = "Kickstart your Java (Maven) project",
        showDefaultValues = true, abbreviateSynopsis = true, version = "Mantra version %s",
        mixinStandardHelpOptions = true)
class Arguments {

    @Parameters(description = "Project's name")
    String name;

    @Option(names = {"--directory", "-d"}, description = "Directory where the project will be created")
    String directory = System.getProperty("user.home");

    @Option(names = {"--group", "-g"}, description = "Project's groupId")
    String groupId = "com.example";

    @Option(names = {"--artifact", "-a"}, description = "Project's artifactId", defaultValue = "project's name")
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

}
