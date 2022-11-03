package com.github.deetree.mantra;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Test
public class CreateProjectIT {

    private final Path homePath = Path.of(SystemProperty.USER_HOME.toString());
    private final String projectName = "testProject";
    private final Path configFilePath = homePath.resolve(".mantra.config");
    private final Path configFileBackupPath = homePath.resolve(".mantra.config.backup");

    @BeforeClass
    private void backupConfig() throws IOException {
        if (new File(configFilePath.toUri()).exists())
            Files.move(configFilePath, configFileBackupPath);
    }

    public void shouldUseProjectNameIfNoArtifactProvided() throws IOException {
        //g
        String[] args = new String[]{"-d", homePath.toString(), projectName, "-l"};
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
        String[] args = new String[]{"-d", homePath.toString(), "-a", artifact, projectName, "-l"};
        Path directories = Path.of("src", "main", "java", "com", "example", artifact);
        //w
        Main.main(args);
        //t
        Assert.assertTrue(Files.isDirectory(homePath.resolve(projectName).resolve(directories)),
                "The specified artifactId should map to directory named as the artifactId");
        removeDirectory(homePath.resolve(projectName));
    }

    private void removeDirectory(Path path) throws IOException {
        Remover.deleteDirectory(path);
    }

    @AfterMethod
    private void deleteTempConfig() throws IOException {
        Files.delete(configFilePath);
    }

    @AfterClass
    private void restoreConfig() throws IOException {
        if (new File(configFileBackupPath.toUri()).exists())
            Files.move(configFileBackupPath, configFilePath, StandardCopyOption.REPLACE_EXISTING);
    }
}
