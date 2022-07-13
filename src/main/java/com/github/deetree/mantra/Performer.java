package com.github.deetree.mantra;

import picocli.CommandLine;

/**
 * @author Mariusz Bal
 */
class Performer {
    private final Trio parsingResult;

    Performer(Trio parsingResult) {
        this.parsingResult = parsingResult;
    }

    void execute() {
        CommandLine cmd = parsingResult.cmd();
        CommandLine.ParseResult result = parsingResult.result();
        Helper usage = new UsageHelper(cmd, result);
        Helper version = new VersionHelper(cmd, result);

        if (!helpUsed(usage, version)) {
            System.out.println("Arguments can be processed. No help was demanded.");
        }
    }

    private boolean helpUsed(Helper usage, Helper version) {
        return usage.checkHelpRequired() || version.checkHelpRequired();
    }
}
