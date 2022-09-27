package com.github.deetree.mantra.oscmd;

import com.github.deetree.mantra.OS;
import com.github.deetree.mantra.OperatingSystem;
import com.github.deetree.mantra.Remover;
import com.github.deetree.mantra.printer.Printer;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Test
public class CreateInitCommitCommandIT {

    private final Printer printer = Printer.getDefault();

    public void shouldCreateCommit() throws IOException {
        //g
        Path dirPath = Files.createTempDirectory("gitTempCommit");
        Path gitRefsHeadsPath = dirPath.resolve(Path.of(".git", "refs", "heads"));
        Files.createFile(dirPath.resolve("sampleFile"));
        OS os = new OperatingSystem().identify();
        new InitializeGitCommand(os, dirPath, printer).execute();
        new LocalGitUserConfigCommand(os, dirPath, "test", "test@test.com", printer).execute();
        //w
        new CreateInitCommitCommand(os, dirPath, printer).execute();
        //t
        SoftAssert sa = new SoftAssert();
        sa.assertTrue(Files.isDirectory(gitRefsHeadsPath),
                "The git directory should exist along with its subdirectories");
        sa.assertTrue(Files.list(gitRefsHeadsPath).findAny().isPresent(), "The first commit should be created");
        sa.assertAll();

        Remover.deleteDirectory(dirPath);
    }

}