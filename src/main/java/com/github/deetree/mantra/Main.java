package com.github.deetree.mantra;

/**
 * @author Mariusz Bal
 */
class Main {
    public static void main(String[] args) {
        Trio parsingResult = new CLIParser(args).parse();
        new Performer(parsingResult).execute();
    }
}
