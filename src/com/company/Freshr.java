package com.company;

import picocli.CommandLine.*;

/**
 * Interface between user and system in facade pattern.
 */
@Command(name = "Freshr")
public class Freshr implements Runnable {
    @Option(names = "-f", description = "Function selector. Possible values 1-8.")
    Integer functionSelector;

    @Option(names = "-n", description = "Station name")
    String stationName;

    @Option(names = {"-h", "--help"}, usageHelp = true, description = "display a help message")
    private boolean helpRequested = false;

    FreshrFacade freshrFacade;

    @Override
    public void run() {
        freshrFacade = new FreshrFacade();
        switch (this.functionSelector) {
            case 1:
                StationIndex si = freshrFacade.getStationIndex(stationName);
                System.out.println(si);
                break;
            default:
                break;
        }
    }
}