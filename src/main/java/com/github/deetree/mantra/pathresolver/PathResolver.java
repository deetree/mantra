package com.github.deetree.mantra.pathresolver;

import java.nio.file.Path;

/**
 * Project paths resolver.
 *
 * @author Mariusz Bal
 */
public class PathResolver {

    private final String directory;
    private final String name;
    private final String groupId;
    private final String artifactId;

    /**
     * Create path resolver.
     *
     * @param directory  project's parent directory path
     * @param name       project name
     * @param groupId    project's groupId
     * @param artifactId project's artifactId
     */
    public PathResolver(String directory, String name, String groupId, String artifactId) {
        this.directory = directory;
        this.name = name;
        this.groupId = groupId;
        this.artifactId = artifactId;
    }

    /**
     * Resolve all project paths.
     *
     * @return all project paths object
     */
    public ResolvedPaths resolvePaths() {
        Path projectPath = new ProjectPathResolver(directory, name).resolve();
        Path sourcesPath = new SourcePathResolver(projectPath).resolve();
        Path packagePath = new PackagePathResolver(groupId, artifactId).resolve();
        Path javaMainFilesPath = new JavaFilesPathResolver(sourcesPath, Directory.MAIN, packagePath).resolve();
        Path mainResourcesPath = new ResourcesPathResolver(sourcesPath, Directory.MAIN).resolve();
        Path javaTestFilesPath = new JavaFilesPathResolver(sourcesPath, Directory.TEST, packagePath).resolve();
        Path testResourcesPath = new ResourcesPathResolver(sourcesPath, Directory.TEST).resolve();
        return new ResolvedPaths(projectPath, sourcesPath, packagePath, javaMainFilesPath,
                mainResourcesPath, javaTestFilesPath, testResourcesPath);
    }
}
