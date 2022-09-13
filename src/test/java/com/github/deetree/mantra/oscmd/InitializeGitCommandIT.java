package com.github.deetree.mantra.oscmd;

import com.github.deetree.mantra.OperatingSystem;
import com.github.deetree.mantra.Result;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;

@Test
public class InitializeGitCommandIT {

    public void shouldInitializeGitRepository() throws IOException {
        //g
        Path dirPath = Files.createTempDirectory(Path.of(System.getProperty("user.home")), "gitTempInit");
        Path gitPath = dirPath.resolve(".git");
        //w
        Result result = new InitializeGitCommand(new OperatingSystem().identify(), dirPath).execute();
        //t
        SoftAssert sa = new SoftAssert();
        sa.assertEquals(result, Result.OK, "The git repo initialization should return successfully");
        sa.assertTrue(Files.isDirectory(gitPath),
                "The .git directory should be created when repo is initialized");
        sa.assertTrue(Files.list(gitPath).findAny().isPresent(),
                "The .git directory should not be empty");
        sa.assertAll();

        Files.walk(dirPath)
                .sorted(Comparator.reverseOrder())
                .map(Path::toFile)
                .forEach(File::delete);
    }
}