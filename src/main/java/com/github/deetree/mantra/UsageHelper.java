package com.github.deetree.mantra;

import picocli.CommandLine;

/**
 * @author Mariusz Bal
 */
class UsageHelper implements Helper {

    private final CommandLine cmd;

    UsageHelper(CommandLine cmd) {
        this.cmd = cmd;
    }

    @Override
    public boolean checkHelpRequired() {
        boolean usageHelpRequested = cmd.isUsageHelpRequested();
        if (usageHelpRequested)
            printHelp();
        return usageHelpRequested;
    }

    @Override
    public void printHelp() {
        cmd.usage(System.out);
    }
}
