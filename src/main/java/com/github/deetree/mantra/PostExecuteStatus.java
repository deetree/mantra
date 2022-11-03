package com.github.deetree.mantra;

/**
 * Status informing after certain task is executed.
 * This interface should be implemented by classes which post-execution status should be printed out to the user.
 *
 * @author Mariusz Bal
 */
public interface PostExecuteStatus {

    /**
     * Prepare message for successful task completion.
     *
     * @return success message
     */
    String makePostExecuteSuccessStatus();

    /**
     * Prepare message for unsuccessful task completion.
     *
     * @return failure message
     */
    String makePostExecuteErrorStatus();
}
