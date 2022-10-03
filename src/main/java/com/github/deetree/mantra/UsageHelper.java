package com.github.deetree.mantra;

import picocli.CommandLine;

/**
 * Usage helper class.
 *
 * @author Mariusz Bal
 */
class UsageHelper implements Helper {

    private final CommandLine cmd;

    /**
     * Usage helper representation.
     *
     * @param cmd parsed command line interpreter
     */
    UsageHelper(CommandLine cmd) {
        this.cmd = cmd;
    }

    @Override
    public boolean checkHelpRequired() {
        return cmd.isUsageHelpRequested();
    }
}
