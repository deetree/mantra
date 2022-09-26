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
        return cmd.isUsageHelpRequested();
    }
}
