package com.github.deetree.mantra.creator;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Test
public class GitignoreCreatorIT {

    public void shouldCopyGitignoreToDirectory() throws IOException {
        //g
        Path directory = Path.of(System.getProperty("user.home"));
        Path gitignore = directory.resolve(".gitignore");
        Creator creator = new GitignoreCreator(directory);
        //w
        creator.create();
        //t
        SoftAssert sa = new SoftAssert();
        sa.assertTrue(Files.isRegularFile(gitignore), "The .gitignore should be a file");
        sa.assertTrue(Files.deleteIfExists(gitignore), "The .gitignore file should exist");
        sa.assertAll();
    }

}