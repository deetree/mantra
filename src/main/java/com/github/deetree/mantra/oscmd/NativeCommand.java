package com.github.deetree.mantra.oscmd;

import com.github.deetree.mantra.OS;
import com.github.deetree.mantra.Result;
import com.github.deetree.mantra.Status;
import com.github.deetree.mantra.printer.Level;
import com.github.deetree.mantra.printer.Message;
import com.github.deetree.mantra.printer.Printer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Native command executing.
 *
 * @author Mariusz Bal
 */
interface NativeCommand extends Status {

    /**
     * Execute the native command.
     *
     * @return execution result
     */
    Result execute();

    /**
     * Execute native command in the OS shell through {@link ProcessBuilder}.
     *
     * @param os        operating system
     * @param directory working directory
     * @param command   command to be executed in the shell
     * @param printer   printer for output printing
     * @return result of native command execution
     */
    default Result execute(OS os, Path directory, String command, Printer printer) {
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.directory(directory.toFile());
        processBuilder.command(joinCommand(os, command));
        try {
            Process process = processBuilder.start();
            readProcessOutput(process, printer);
            int exitCode = process.waitFor();
            return exitCode == 0 ? Result.OK : Result.ERROR;
        } catch (IOException | InterruptedException e) {
            return Result.ERROR;
        }
    }

    private void readProcessOutput(Process process, Printer printer) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            printer.print(new Message(Level.SYSTEM, line));
        }
    }

    private List<String> joinCommand(OS os, String command) {
        return new ArrayList<>() {{
            addAll(os.shellCommand());
            add(command);
        }};
    }
}
