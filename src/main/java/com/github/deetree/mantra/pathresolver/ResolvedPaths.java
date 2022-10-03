package com.github.deetree.mantra.pathresolver;

import java.nio.file.Path;

/**
 * Project resolved paths for transferring between packages
 *
 * @author Mariusz Bal
 */
public record ResolvedPaths(Path projectPath,
                            Path sourcesPath,
                            Path packagePath,
                            Path javaMainFilesPath,
                            Path mainResourcesPath,
                            Path javaTestFilesPath,
                            Path testResourcesPath) {}
