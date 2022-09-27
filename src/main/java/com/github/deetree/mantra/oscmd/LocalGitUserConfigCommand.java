package com.github.deetree.mantra.oscmd;

import com.github.deetree.mantra.ActionException;
import com.github.deetree.mantra.OS;
import com.github.deetree.mantra.Result;
import com.github.deetree.mantra.printer.Printer;

import java.nio.file.Path;

/**
 * @author Mariusz Bal
 */
class LocalGitUserConfigCommand implements NativeCommand {

    private final OS os;
    private final Path projectPath;
    private final String user;
    private final String email;
    private final Printer printer;

    LocalGitUserConfigCommand(OS os, Path projectPath, String user, String email, Printer printer) {
        this.os = os;
        this.projectPath = projectPath;
        this.user = user;
        this.email = email;
        this.printer = printer;
    }

    @Override
    public String preExecuteStatus() {
        return "Configuring git repository";
    }

    @Override
    public Result execute() {
        if (isValid(user))
            configure("name", user);
        if (isValid(email))
            configure("email", email);
        return Result.OK;
    }

    @Override
    public String postExecuteStatus() {
        return "Git repository configured";
    }

    private boolean isValid(CharSequence element) {
        return element != null && !element.isEmpty();
    }

    private Result configure(String configElement, String value) {
        if (execute(os, projectPath, "git config user.%s \"%s\"".formatted(configElement, value), printer) != Result.OK)
            throw new ActionException("An exception occurred during git user %s configuration"
                    .formatted(configElement));
        return Result.OK;
    }

}
