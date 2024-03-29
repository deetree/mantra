package com.github.deetree.mantra.pathresolver;

import java.io.File;
import java.nio.file.Path;

/**
 * Package path resolver for combining groupId and artifactId.
 *
 * @author Mariusz Bal
 */
class PackagePathResolver implements Resolver {

    private final String groupId;
    private final String artifactId;

    /**
     * Package path resolver.
     *
     * @param groupId    project's groupId
     * @param artifactId project's artifactId
     */
    PackagePathResolver(String groupId, String artifactId) {
        this.groupId = groupId;
        this.artifactId = artifactId;
    }

    @Override
    public Path resolve() {
        return Path.of(replaceGroupId(), artifactId);
    }

    private String replaceGroupId() {
        return groupId.replace('.', File.separatorChar);
    }
}
