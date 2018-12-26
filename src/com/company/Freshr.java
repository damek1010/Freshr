package com.company;

import picocli.CommandLine.*;

import java.util.LinkedList;

/**
 * Main class of app. It's interface between user and system in facade pattern.
 */
@Command(name = "Freshr")
public class Freshr implements Runnable {
    @Option(names = "-f", description = "Function selector. Possible values 1-8.")
    Integer functionSelector;

    @Option(names = {"-h", "--help"}, usageHelp = true, description = "display a help message")
    private boolean helpRequested = false;

    LinkedList<Station> stations;

    ApiProvider provider;

    /**
     * Initialize essential fields
     */
    private void init() {
        provider = new GiosApiProvider();
        stations = provider.fetchStations();
    }

    @Override
    public void run() {
        init();
        switch (this.functionSelector) {
            case 1:
                System.out.println(stations);
                break;
            default:
                break;
        }
    }
}
