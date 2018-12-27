package com.company;

import picocli.CommandLine.*;

/**
 * Interface between user and system in facade pattern.
 */
@Command(name = "Freshr")
public class FreshrFacade implements Runnable {
    @Option(names = "-f", description = "Function selector. Possible values 1-8.")
    Integer functionSelector;

    @Option(names = "-n", description = "Station name")
    String stationName;

    @Option(names = {"-h", "--help"}, usageHelp = true, description = "display a help message")
    private boolean helpRequested = false;

    Freshr freshr;

    @Override
    public void run() {
        freshr = new Freshr();
        switch (this.functionSelector) {
            case 1:
                StationIndex si = freshr.getStationIndex(stationName);
                System.out.println(si);
                break;
            default:
                break;
        }
    }
}