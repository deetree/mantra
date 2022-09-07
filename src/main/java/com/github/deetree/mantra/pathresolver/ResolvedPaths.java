package com.github.deetree.mantra.pathresolver;

import java.nio.file.Path;

/**
 * @author Mariusz Bal
 */
public record ResolvedPaths(Path projectPath,
                     Path sourcesPath,
                     Path packagePath,
                     Path javaMainFilesPath,
                     Path mainResourcesPath,
                     Path javaTestFilesPath,
                     Path testResourcesPath) {}
