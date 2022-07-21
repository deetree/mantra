package com.github.deetree.mantra;

import java.nio.file.Path;

/**
 * @author Mariusz Bal
 */
class LocalGitUserConfigCommand implements NativeCommand {

    private final OS os;
    private final Path projectPath;
    private final String user;
    private final String email;

    LocalGitUserConfigCommand(OS os, Path projectPath, String user, String email) {
        this.os = os;
        this.projectPath = projectPath;
        this.user = user;
        this.email = email;
    }

    @Override
    public Result execute() {
        if (isValid(user))
            configure("name", user);
        if (isValid(email))
            configure("email", email);
        return Result.OK;
    }

    private boolean isValid(CharSequence element) {
        return element != null && !element.isEmpty();
    }

    private Result configure(String configElement, String value) {
        if (execute(os, projectPath, "git config user.%s '%s'".formatted(configElement, value)) != Result.OK)
            throw new ActionException("An exception occurred during git user %s configuration"
                    .formatted(configElement));
        return Result.OK;
    }

}
