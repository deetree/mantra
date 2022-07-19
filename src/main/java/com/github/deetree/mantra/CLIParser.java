package com.github.deetree.mantra;

import picocli.CommandLine;

import static picocli.CommandLine.ParseResult;

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
        ParseResult parseResult = cmd.parseArgs(args);
        return new Trio(cmd, parseResult, arguments);
    }
}