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
            new Arguments()).parse().cmd());
    private final Helper usageHelper = new UsageHelper(new CLIParser(new String[]{"-h"},
            new Arguments()).parse().cmd());
    private ByteArrayOutputStream output;

    @BeforeMethod(onlyForGroups = "output")
    public void setUp() {
        output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
    }

    @AfterMethod(onlyForGroups = "output")
    public void cleanUp() {
        System.setOut(System.out);
    }

    public void shouldReturnUsageHelpRequested() {
        assertTrue(usageHelper.checkHelpRequired());
    }

    @Test(groups = "output")
    public void shouldPrintUsageHelp() {
        usageHelper.printHelp();
        assertTrue(output.toString().contains("Usage"));
    }

    public void shouldReturnVersionHelpRequested() {
        assertTrue(versionHelper.checkHelpRequired());
    }

    @Test(groups = "output")
    public void shouldPrintVersionHelp() {
        versionHelper.printHelp();
        assertTrue(output.toString().contains("Mantra version"));
    }
}
