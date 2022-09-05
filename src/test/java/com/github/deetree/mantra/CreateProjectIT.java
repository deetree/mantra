package com.github.deetree.mantra;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;

@Test
public class CreateProjectIT {

    private final Path homePath = Path.of(System.getProperty("user.home"));
    private final String projectName = "testProject";

    public void shouldUseProjectNameIfNoArtifactProvided() throws IOException {
        //g
        String[] args = new String[]{"-d", homePath.toString(), projectName};
        Path directories = Path.of("src", "main", "java", "com", "example", projectName);
        //w
        Main.main(args);
        //t
        Assert.assertTrue(Files.isDirectory(homePath.resolve(projectName).resolve(directories)),
                "The unspecified artifactId should map to directory named as project's name");
        removeDirectory(homePath.resolve(projectName));
    }

    public void shouldUseArtifactIfProvided() throws IOException {
        String artifact = "myArtifact";
        String[] args = new String[]{"-d", homePath.toString(), "-a", artifact, projectName};
        Path directories = Path.of("src", "main", "java", "com", "example", artifact);
        //w
        Main.main(args);
        //t
        Assert.assertTrue(Files.isDirectory(homePath.resolve(projectName).resolve(directories)),
                "The specified artifactId should map to directory named as the artifactId");
        removeDirectory(homePath.resolve(projectName));
    }

    private void removeDirectory(Path path) throws IOException {
        Files.walk(path)
                .sorted(Comparator.reverseOrder())
                .map(Path::toFile)
                .forEach(File::delete);
    }
}
