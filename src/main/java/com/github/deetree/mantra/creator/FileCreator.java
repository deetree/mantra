package com.github.deetree.mantra.creator;

/**
 * @author Mariusz Bal
 */
interface FileCreator {

    String preExecuteStatus();

    void create();

    String postExecuteStatus();
}
