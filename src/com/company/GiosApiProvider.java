package com.company;

import com.google.gson.Gson;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * Class of GIOŚ Provider. It is fetching data from api shared by Polish government.
 */
public class GiosApiProvider implements ApiProvider {
    @Override
    public LinkedList<Station> fetchStations() {
        String urlString = "http://api.gios.gov.pl/pjp-api/rest/station/findAll";

        ApiReader ar = new ApiReader();
        String stationsString = ar.readURL(urlString);

        Gson gson = new Gson();
        Station[] stationsArray = gson.fromJson(stationsString, Station[].class);

        return new LinkedList<>(Arrays.asList(stationsArray));
    }
}
