package com.github.deetree.mantra;

import java.nio.file.Path;

/**
 * @author Mariusz Bal
 */
@FunctionalInterface
interface PathResolver {

    Path resolve();

}
