package com.github.deetree.mantra.interactivemode;

import com.github.deetree.mantra.Reader;
import com.github.deetree.mantra.printer.Printer;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.testng.Assert.assertTrue;

@Test
public class InteractiveProjectCreatorTest {

    private final String mockInteractiveInput = IntStream.range(0, 11).mapToObj(i -> System.lineSeparator())
            .collect(Collectors.joining());

    @BeforeMethod
    public void setUpMockInteractiveInput() {
        System.setIn(new ByteArrayInputStream(mockInteractiveInput.getBytes()));
    }

    @AfterMethod
    public void cleanUpMockInteractiveInput() {
        System.setIn(System.in);
    }

    public void shouldReturnEmptyArgumentsIfAllSkipped() {
        //g
        //w
        List<String> returnedArgs = new InteractiveProjectCreator(Printer.getDefault(), Reader.getDefault())
                .prepareArguments();
        //t
        assertTrue(returnedArgs.isEmpty(), "When all properties are skipped in interactive project " +
                "creating mode the returned arg stack should be empty");
    }
}