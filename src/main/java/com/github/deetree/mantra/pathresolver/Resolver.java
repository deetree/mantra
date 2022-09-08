package com.github.deetree.mantra.pathresolver;

import java.nio.file.Path;

/**
 * @author Mariusz Bal
 */
@FunctionalInterface
interface Resolver {

    Path resolve();

}
