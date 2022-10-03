package com.github.deetree.mantra;

import picocli.CommandLine;

/**
 * Version helper class.
 *
 * @author Mariusz Bal
 */
class VersionHelper implements Helper {

    private final CommandLine cmd;

    VersionHelper(CommandLine cmd) {
        this.cmd = cmd;
    }

    @Override
    public boolean checkHelpRequired() {
        return cmd.isVersionHelpRequested();
    }
}
