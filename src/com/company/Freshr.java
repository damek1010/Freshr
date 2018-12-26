package com.company;

import picocli.CommandLine.*;

@Command(name = "Freshr")
public class Freshr implements Runnable {
    @Option(names = "-f", description = "Function selector. Possible values 1-8.")
    Integer functionSelector;

    @Option(names = {"-h", "--help"}, usageHelp = true, description = "display a help message")
    private boolean helpRequested = false;

    @Override
    public void run() {
        switch (this.functionSelector) {
            case 1:
                System.out.println("Indeks statcji pogodowej");
                break;
            default:
                break;
        }
    }
}
