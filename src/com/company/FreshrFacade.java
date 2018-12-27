package com.company;

import java.util.LinkedList;

/**
 * Main class of app.
 */
public class FreshrFacade {
    /**
     * List of all stations fetched from source
     */
    LinkedList<Station> stations;

    /**
     * Api Provider used to get data
     */
    ApiProvider provider;

    /**
     * Initialize essential fields
     */
    public FreshrFacade() {
        provider = new GiosApiProvider();
        stations = provider.fetchStations();
    }

    public StationIndex getStationIndex(String stationName) {
        Station station = getStationByName(stationName);
        StationIndex index = provider.fetchStationIndex(station.getID());
        return index;
    }

    private Station getStationByName(String stationName) {
        Station result = null;
        for (Station station : stations) {
            if (station.getName().toUpperCase().equals(stationName.toUpperCase())) {
                result = station;
                break;
            }
        }
        return result;
    }
}
