package com.github.deetree.mantra;

import picocli.CommandLine;

import static picocli.CommandLine.Help;

/**
 * @author Mariusz Bal
 */
class VersionHelper implements Helper {

    private final CommandLine cmd;

    VersionHelper(CommandLine cmd) {
        this.cmd = cmd;
    }

    @Override
    public boolean checkHelpRequired() {
        boolean versionHelpRequested = cmd.isVersionHelpRequested();
        if (versionHelpRequested)
            printHelp();
        return versionHelpRequested;
    }

    @Override
    public void printHelp() {
        cmd.printVersionHelp(System.out, Help.Ansi.AUTO, "1.0");
    }
}
