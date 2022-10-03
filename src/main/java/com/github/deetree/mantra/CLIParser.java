package com.github.deetree.mantra;

import picocli.CommandLine;

/**
 * Command line arguments parser against {@link Arguments} class fields.
 *
 * @author Mariusz Bal
 */
class CLIParser {

    private final String[] args;
    private final Arguments arguments;

    /**
     * Instantiate command line arguments parser
     *
     * @param args      command line arguments
     * @param arguments options to be matched by cmd-line args
     */
    CLIParser(String[] args, Arguments arguments) {
        this.args = args;
        this.arguments = arguments;
    }

    /**
     * Parse command line arguments so the {@link Arguments} fields are filled with appropriate values.
     *
     * @return command line interpreter
     */
    CommandLine parse() {
        CommandLine cmd = new CommandLine(arguments);
        cmd.setOverwrittenOptionsAllowed(true);
        cmd.setUnmatchedArgumentsAllowed(true);
        cmd.execute(args);
        return cmd;
    }
}
