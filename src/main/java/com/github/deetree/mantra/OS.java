package com.github.deetree.mantra;

import java.util.List;

/**
 * @author Mariusz Bal
 */
public enum OS {
    WINDOWS(List.of("cmd.exe", "/c")),
    LINUX(List.of("sh", "-c"));

    private final List<String> shellCommand;

    OS(List<String> shellCommand) {
        this.shellCommand = shellCommand;
    }

    public List<String> shellCommand() {
        return shellCommand;
    }

}
