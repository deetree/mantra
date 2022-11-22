package com.github.deetree.mantra.interactivemode;

import com.github.deetree.mantra.Reader;
import com.github.deetree.mantra.printer.Level;
import com.github.deetree.mantra.printer.Message;
import com.github.deetree.mantra.printer.Printer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Requests for properties' values to allow interactive project creating.
 *
 * @author Mariusz Bal
 */
public class InteractiveProjectCreator {

    private final Printer printer;
    private final Reader reader; //todo

    private final List<String> preparedArgs = new ArrayList<>();

    public InteractiveProjectCreator(Printer printer, Reader reader) {
        this.printer = printer;
        this.reader = reader;
    }

    public List<String> prepareArguments() {
        Arrays.stream(InteractiveProjectProperty.values())
                .map(InteractiveProjectProperty::option)
                .forEach(this::consume);
        Collections.reverse(preparedArgs);
        return preparedArgs;
    }

    private void consume(InteractiveOption option) {
        if (!gitSkipRequested() || !isGitOption(option)) {
            printer.print(new Message(Level.SYSTEM, option.toString()));
            parseInput(reader.readLine(), option.type(), option.flag());
        }
    }

    private boolean isGitOption(InteractiveOption option) {
        return option.prompt().contains("git");
    }

    private boolean gitSkipRequested() {
        return preparedArgs.contains(InteractiveProjectProperty.SKIP_GIT.option().flag());
    }

    private void parseInput(String line, Class<?> type, String flag) {
        if (type == Boolean.class)
            checkBooleanConfirmed(line, flag);
        else if (!line.isBlank()) {
            if (!flag.isBlank())
                preparedArgs.add(flag);
            preparedArgs.add(line);
        }
    }

    private void checkBooleanConfirmed(String line, String flag) {
        if (line.equalsIgnoreCase("y"))
            preparedArgs.add(flag);
    }
}
