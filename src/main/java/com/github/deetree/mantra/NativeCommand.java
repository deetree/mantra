package com.github.deetree.mantra;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Mariusz Bal
 */
@FunctionalInterface
interface NativeCommand {

    Result execute();

    default Result execute(OS os, Path directory, String command) {
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.directory(directory.toFile());
        processBuilder.command(joinCommand(os, command));
        try {
            Process process = processBuilder.start();
            readProcessOutput(process);
            int exitCode = process.waitFor();
            return exitCode == 0 ? Result.OK : Result.ERROR;
        } catch (IOException | InterruptedException e) {
            return Result.ERROR;
        }
    }

    private void readProcessOutput(Process process) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        Printer printer = new ConsolePrinter();
        while ((line = reader.readLine()) != null) {
            printer.print(line);
        }
    }

    private List<String> joinCommand(OS os, String command) {
        return new ArrayList<>() {{
            addAll(os.shellCommand());
            add(command);
        }};
    }
}
