package com.github.deetree.mantra.pathresolver;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.nio.file.Path;

import static org.testng.Assert.assertEquals;

@Test
public class PathResolverTest {

    private final String directory = System.getProperty("java.io.tmpdir");
    private final String projectName = "exampleProject";
    private final String src = "src";
    private final String group = "com.xyz.abc.def";
    private final String artifact = "cosmonaut";
    private final char separator = File.separatorChar;
    private Resolver resolver;

    public void shouldCreateProperProjectPath() {
        //g
        var expected = Path.of(directory, projectName);
        //w
        resolver = new ProjectPathResolver(directory, projectName);
        //t
        assertEquals(resolver.resolve(), expected, "The project path is not as expected");
    }

    public void shouldCreateProperSourcePath() {
        //g
        var expected = Path.of(directory, projectName, src);
        //w
        resolver = new SourcePathResolver(Path.of(directory, projectName));
        //t
        assertEquals(resolver.resolve(), expected, "The source path is not as expected");
    }

    public void shouldCreateProperPackagePath() {
        //g
        var expected = Path.of(replaceGroup(), artifact);
        //w
        resolver = new PackagePathResolver(group, artifact);
        //t
        assertEquals(resolver.resolve(), expected, "The package path is not as expected");
    }

    @DataProvider
    private Object[][] parentDirectoryProvider() {
        return new Object[][]{
                {Directory.valueOf("main".toUpperCase())},
                {Directory.valueOf("test".toUpperCase())}
        };
    }

    @Test(dataProvider = "parentDirectoryProvider")
    public void shouldCreateProperJavaPath(Directory parent) {
        //g
        var expected = Path.of(directory, projectName, src, parent.toString(),
                "java", replaceGroup(), artifact);
        //w
        resolver = new JavaFilesPathResolver(
                new SourcePathResolver(new ProjectPathResolver(directory, projectName).resolve()).resolve(),
                parent,
                new PackagePathResolver(group, artifact).resolve()
        );
        //t
        assertEquals(resolver.resolve(), expected, "The java files path is not as expected");
    }

    @Test(dataProvider = "parentDirectoryProvider")
    public void shouldCreateProperResourcesPath(Directory parent) {
        //g
        var expected = Path.of(directory, projectName, src, parent.toString(), "resources");
        //w
        resolver = new ResourcesPathResolver(
                new SourcePathResolver(new ProjectPathResolver(directory, projectName).resolve()).resolve(),
                parent
        );
        //t
        assertEquals(resolver.resolve(), expected, "The resources path is not as expected");
    }

    private String replaceGroup() {
        return group.replace('.', separator);
    }
}
