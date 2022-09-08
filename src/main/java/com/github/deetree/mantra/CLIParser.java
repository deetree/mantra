package com.github.deetree.mantra;

import picocli.CommandLine;

/**
 * @author Mariusz Bal
 */
class CLIParser {

    private final String[] args;

    CLIParser(String[] args) {this.args = args;}

    Trio parse() {
        Arguments arguments = new Arguments();
        CommandLine cmd = new CommandLine(arguments);
        cmd.setOverwrittenOptionsAllowed(true);
        cmd.setUnmatchedArgumentsAllowed(true);
        cmd.execute(args);
        return new Trio(cmd, null, arguments);
    }
}
