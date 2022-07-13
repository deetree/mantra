package com.github.deetree.mantra;

import picocli.CommandLine;

import static picocli.CommandLine.Help;
import static picocli.CommandLine.ParseResult;

/**
 * @author Mariusz Bal
 */
class VersionHelper implements Helper {

    private final CommandLine cmd;
    private final ParseResult result;

    VersionHelper(CommandLine cmd, ParseResult result) {
        this.cmd = cmd;
        this.result = result;
    }

    @Override
    public boolean checkHelpRequired() {
        boolean versionHelpRequested = result.isVersionHelpRequested();
        if (versionHelpRequested)
            printHelp();
        return versionHelpRequested;
    }

    @Override
    public void printHelp() {
        cmd.printVersionHelp(System.out, Help.Ansi.AUTO, "1.0");
    }
}
