package com.github.deetree.mantra;

/**
 * Status informing before certain task is executed.
 * This interface should be implemented by classes which pre-execution status should be printed out to the user.
 *
 * @author Mariusz Bal
 */
@FunctionalInterface
public interface PostExecuteStatus {

    /**
     * Prepare message for printout prior to the task execution.
     *
     * @return before execution message
     */
    String makePreExecuteStatus();
}
