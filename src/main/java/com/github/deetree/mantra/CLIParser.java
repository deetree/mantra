package com.github.deetree.mantra;

import picocli.CommandLine;

/**
 * @author Mariusz Bal
 */
class CLIParser {

    private final String[] args;
    private final Arguments arguments;

    CLIParser(String[] args, Arguments arguments) {
        this.args = args;
        this.arguments = arguments;
    }

    Trio parse() {
        CommandLine cmd = new CommandLine(arguments);
        cmd.setOverwrittenOptionsAllowed(true);
        cmd.setUnmatchedArgumentsAllowed(true);
        cmd.execute(args);
        return new Trio(cmd, null, arguments);
    }
}
