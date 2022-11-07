package com.github.deetree.mantra;

import com.github.deetree.mantra.printer.Level;
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

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

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
        //g
        String[] args = new String[]{"-h"};
        //w
        Main.main(args);
        //t
        assertFalse(output.toString().isEmpty(), "When helper flag is provided, " +
                "the output should be printed out");
    }

    @Test(groups = "config")
    public void shouldPrintOutputConfigFlag() {
        //g
        String[] args = new String[]{"-c"};
        //w
        Main.main(args);
        //t
        assertFalse(output.toString().isEmpty(), "When config flag is provided, " +
                "the output should be printed out");
    }

    @Test(groups = "config")
    public void shouldPrintOutputConfigSilentFlag() {
        //g
        String[] args = new String[]{"-c", "-s"};
        //w
        Main.main(args);
        //t
        assertFalse(output.toString().isEmpty(), "When config and silent flag is provided, " +
                "the output should be printed out");
    }

    public void shouldNotPrintOutputSilentFlag() throws IOException {
        //g
        String name = "testProjectShouldNotPrintSilentFlag";
        String[] args = new String[]{"-d", SystemProperty.TMP_DIR.toString(), name, "-s", "-l",
                "-u", "user", "-e", "email"};
        //w
        Main.main(args);
        //t
        assertTrue(output.toString().isEmpty(), "When silent flag is provided, " +
                "the output should not be printed out");
        Remover.deleteDirectory(Path.of(SystemProperty.TMP_DIR.toString(), name));
    }

    public void shouldPrintErrorsSilentFlag() throws IOException {
        //g
        String name = "testProjectShouldPrintErrorsSilentFlag";
        String[] args = new String[]{"-d", SystemProperty.TMP_DIR.toString(), name, "-s", "-l"};
        //w
        Main.main(args);
        Main.main(args);
        //t
        SoftAssert sa = new SoftAssert();
        sa.assertFalse(output.toString().isEmpty());
        sa.assertTrue(output.toString().contains(Level.ERROR.toString()));
        sa.assertFalse(output.toString().contains(Level.INFO.toString()));
        sa.assertAll("When silent flag is provided only errors should be printed out");
        Remover.deleteDirectory(Path.of(SystemProperty.TMP_DIR.toString(), name));
    }

    public void shouldPrintOutputWithoutSilentFlag() throws IOException {
        //g
        String name = "testProjectShouldPrintOutputWithoutSilentFlag";
        String[] args = new String[]{"-d", SystemProperty.TMP_DIR.toString(), name, "-l"};
        //w
        Main.main(args);
        //t
        assertFalse(output.toString().isEmpty(), "When no silent flag is provided, " +
                "the output should be printed out");
        Remover.deleteDirectory(Path.of(SystemProperty.TMP_DIR.toString(), name));
    }
}
