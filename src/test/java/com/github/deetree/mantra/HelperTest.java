package com.github.deetree.mantra;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.testng.Assert.assertTrue;

@Test
public class HelperTest {

    private final Helper versionHelper = new VersionHelper(new CLIParser(new String[]{"-V"},
            new Arguments()).parse());
    private final Helper usageHelper = new UsageHelper(new CLIParser(new String[]{"-h"},
            new Arguments()).parse());

    public void shouldReturnUsageHelpRequested() {
        assertTrue(usageHelper.checkHelpRequired());
    }

    public void shouldReturnVersionHelpRequested() {
        assertTrue(versionHelper.checkHelpRequired());
    }
}
