package com.github.deetree.mantra;

import com.github.deetree.mantra.printer.Level;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Path;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Test
public class SilentOutputIT {

    private ByteArrayOutputStream output;
    private final String mockInput = IntStream.range(0, 7).mapToObj(i -> System.lineSeparator())
            .collect(Collectors.joining());

    @BeforeMethod
    public void setUp() {
        output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
    }

    @BeforeMethod(onlyForGroups = "config")
    public void setUpMockInput() {
        System.setIn(new ByteArrayInputStream(mockInput.getBytes()));
    }

    @AfterMethod
    public void cleanUp() {
        System.setOut(System.out);
    }

    @AfterMethod(onlyForGroups = "config")
    public void cleanUpMockInput() {
        System.setIn(System.in);
    }

    public void shouldPrintOutputHelperFlag() {
        String[] args = new String[]{"-h"};
        Main.main(args);
        Assert.assertFalse(output.toString().isEmpty(), "When helper flag is provided, " +
                "the output should be printed out");
    }

    @Test(groups = "config")
    public void shouldPrintOutputConfigFlag() {
        String[] args = new String[]{"-c"};
        Main.main(args);
        Assert.assertFalse(output.toString().isEmpty(), "When config flag is provided, " +
                "the output should be printed out");
    }

    @Test(groups = "config")
    public void shouldPrintOutputConfigSilentFlag() {
        String[] args = new String[]{"-c", "-s"};
        Main.main(args);
        Assert.assertFalse(output.toString().isEmpty(), "When config and silent flag is provided, " +
                "the output should be printed out");
    }

    public void shouldNotPrintOutputSilentFlag() throws IOException {
        String name = "testProject";
        String[] args = new String[]{"-d", SystemProperty.TMP_DIR.toString(), name, "-s", "-l"};
        Main.main(args);
        Assert.assertTrue(output.toString().isEmpty(), "When silent flag is provided, " +
                "the output should not be printed out");
        Remover.deleteDirectory(Path.of(SystemProperty.TMP_DIR.toString(), name));
    }

    public void shouldPrintErrorsSilentFlag() throws IOException {
        String name = "testProject";
        String[] args = new String[]{"-d", SystemProperty.TMP_DIR.toString(), name, "-s", "-l"};
        Main.main(args);
        Main.main(args);
        SoftAssert sa = new SoftAssert();
        sa.assertFalse(output.toString().isEmpty());
        sa.assertTrue(output.toString().contains(Level.ERROR.toString()));
        sa.assertFalse(output.toString().contains(Level.INFO.toString()));
        sa.assertAll("When silent flag is provided only errors should be printed out");
        Remover.deleteDirectory(Path.of(SystemProperty.TMP_DIR.toString(), name));
    }

    public void shouldPrintOutputWithoutSilentFlag() throws IOException {
        String name = "testProject";
        String[] args = new String[]{"-d", SystemProperty.TMP_DIR.toString(), name, "-l"};
        Main.main(args);
        Assert.assertFalse(output.toString().isEmpty(), "When no silent flag is provided, " +
                "the output should be printed out");
        Remover.deleteDirectory(Path.of(SystemProperty.TMP_DIR.toString(), name));
    }
}
