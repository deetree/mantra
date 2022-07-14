package com.github.deetree.mantra;

import java.io.File;
import java.nio.file.Path;

/**
 * @author Mariusz Bal
 */
class PackagePathResolver implements PathResolver {

    private final String groupId;
    private final String artifactId;

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
