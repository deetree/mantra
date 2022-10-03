package com.github.deetree.mantra;

import java.util.List;

/**
 * Operating systems available for detection with their shell commands for native commands executing.
 *
 * @author Mariusz Bal
 */
public enum OS {
    WINDOWS(List.of("cmd.exe", "/c")),
    LINUX(List.of("sh", "-c"));

    private final List<String> shellCommand;

    /**
     * Operating system entry creator
     *
     * @param shellCommand OS command for executing native shell commands
     */
    OS(List<String> shellCommand) {
        this.shellCommand = shellCommand;
    }

    public List<String> shellCommand() {
        return shellCommand;
    }

}
