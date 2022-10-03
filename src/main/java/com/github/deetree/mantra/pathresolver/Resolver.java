package com.github.deetree.mantra.pathresolver;

import java.nio.file.Path;

/**
 * Paths resolver.
 *
 * @author Mariusz Bal
 */
@FunctionalInterface
interface Resolver {

    /**
     * Resolve paths for project files and directories.
     *
     * @return resolved path
     */
    Path resolve();

}
