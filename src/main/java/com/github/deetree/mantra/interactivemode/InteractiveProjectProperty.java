package com.github.deetree.mantra.interactivemode;

/**
 * Properties used to create the project interactively.
 *
 * @author Mariusz Bal
 */
enum InteractiveProjectProperty {
    NAME(new InteractiveOption("", "Enter the name of the project (required)", String.class)),
    DIRECTORY(new InteractiveOption("-d", "Where will the project be created?", String.class)),
    GROUP(new InteractiveOption("-g", "Enter groupId", String.class)),
    ARTIFACT(new InteractiveOption("-a", "Enter artifactId", String.class)),
    MAIN(new InteractiveOption("-m", "Enter main class name", String.class)),
    JAVA(new InteractiveOption("-j", "Enter Java version to use", Integer.class)),
    SKIP_GIT(new InteractiveOption("-n", "Would you like to skip git initialization?", Boolean.class)),
    GIT_USERNAME(new InteractiveOption("-u", "Enter git username", String.class)),
    GIT_EMAIL(new InteractiveOption("-e", "Enter git user email", String.class)),
    SKIP_IDEA(new InteractiveOption("-l", "Would you like to skip IDEA opening?", Boolean.class)),
    SILENT(new InteractiveOption("-s", "Would you like to use silent output?", Boolean.class));

    private final InteractiveOption option;

    /**
     * Interactive mode project property.
     *
     * @param option interactive project option
     */
    InteractiveProjectProperty(InteractiveOption option) {this.option = option;}

    InteractiveOption option() {
        return option;
    }
}
