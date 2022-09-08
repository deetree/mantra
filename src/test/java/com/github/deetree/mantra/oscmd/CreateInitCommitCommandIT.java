package com.github.deetree.mantra.oscmd;

import com.github.deetree.mantra.OS;
import com.github.deetree.mantra.OperatingSystem;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;

@Test
public class CreateInitCommitCommandIT {

    public void shouldCreateCommit() throws IOException {
        //g
        Path dirPath = Files.createTempDirectory(Path.of(System.getProperty("user.home")), "gitTemp");
        Path gitRefsHeadsPath = dirPath.resolve(Path.of(".git", "refs", "heads"));
        Files.createFile(dirPath.resolve("sampleFile"));
        OS os = new OperatingSystem().identify();
        new InitializeGitCommand(os, dirPath).execute();
        new LocalGitUserConfigCommand(os, dirPath, "test", "test@test.com").execute();
        //w
        new CreateInitCommitCommand(os, dirPath).execute();
        //t
        SoftAssert sa = new SoftAssert();
        sa.assertTrue(Files.isDirectory(gitRefsHeadsPath),
                "The git directory should exist along with its subdirectories");
        sa.assertTrue(Files.list(gitRefsHeadsPath).findAny().isPresent(), "The first commit should be created");
        sa.assertAll();

        Files.walk(dirPath)
                .sorted(Comparator.reverseOrder())
                .map(Path::toFile)
                .forEach(File::delete);
    }

}