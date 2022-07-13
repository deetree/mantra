package com.github.deetree.mantra;

import picocli.CommandLine;
import picocli.CommandLine.ParseResult;

/**
 * @author Mariusz Bal
 */
record Trio(CommandLine cmd, ParseResult result, Arguments arguments) {}
