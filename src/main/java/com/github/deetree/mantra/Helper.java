package com.github.deetree.mantra;

/**
 * API for helpers used within the project.
 *
 * @author Mariusz Bal
 */
@FunctionalInterface
interface Helper {

    /**
     * Check whether help has been requested through providing appropriate cmd flag.
     *
     * @return {@code true} if this helper's help has been requested, {@code false} otherwise
     */
    boolean checkHelpRequired();
}
