package com.company;

import picocli.CommandLine.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.LinkedList;

/**
 * Main class of app.
 */
@Command(name = "Freshr")
public class Freshr implements Runnable {
    @Option(names = "-f", description = "Function selector. Possible values 1-8.")
    Integer functionSelector;

    @Option(names = "-n", description = "Station name")
    String stationName;

    @Option(names = "-p", description = "Parameter")
    Parameter parameter;

    @Option(names = "-d", description = "Date")
    String date;

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
            case 2:
                //TODO: Wybieranie najbliższej godziny i rzucanie wyjątkami, kiedy nie ma takiej daty
                LinkedList<Sensor> sensors = freshrFacade.getSensors(stationName);
                Sensor sensor = null;
                for (Sensor sensorElement : sensors) {
                    if (sensorElement.getParameter().equals(parameter)) {
                        sensor = sensorElement;
                        break;
                    }
                }
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    System.out.println(String.format("%s: %f", sensor.getParameter().getName(), sensor.getValue(sdf.parse(date))));
                } catch (ParseException ignored) {

                }
            default:
                break;
        }
    }
}