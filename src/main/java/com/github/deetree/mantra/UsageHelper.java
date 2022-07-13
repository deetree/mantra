package com.github.deetree.mantra;

import picocli.CommandLine;
import picocli.CommandLine.ParseResult;

/**
 * @author Mariusz Bal
 */
class UsageHelper implements Helper {

    private final CommandLine cmd;
    private final ParseResult result;

    UsageHelper(CommandLine cmd, ParseResult result) {
        this.cmd = cmd;
        this.result = result;
    }

    @Override
    public boolean checkHelpRequired() {
        boolean usageHelpRequested = result.isUsageHelpRequested();
        if (usageHelpRequested)
            printHelp();
        return usageHelpRequested;
    }

    @Override
    public void printHelp() {
        cmd.usage(System.out);
    }
}
