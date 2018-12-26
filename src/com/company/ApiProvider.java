package com.company;

import java.util.LinkedList;

/**
 * Common interface of Api Providers. It is fetching data from external sources.
 */
public interface ApiProvider {
    /**
     * @return List of stations fetched from source api
     */
    LinkedList<Station> fetchStations();
}
