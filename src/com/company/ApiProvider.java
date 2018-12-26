package com.company;

import java.util.LinkedList;

public interface ApiProvider {
    LinkedList<Station> fetchStations();
}
