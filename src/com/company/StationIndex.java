package com.company;
import java.util.*;

/**
 * Class represents index of station of specific id
 */
public class StationIndex {
    /**
     * Station ID
     */
    private Integer stationId;
    /**
     * Parameters stored in station index
     */
    HashMap<String, StationIndexParameter> parameters;

    StationIndex() {
        parameters = new HashMap<>();
    }

    @Override
    public String toString() {
        Set entrySet = parameters.entrySet();
        Iterator it = entrySet.iterator();
        StringBuilder sb = new StringBuilder();

        while(it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            sb.append(entry.getKey());
            sb.append(":\n");
            sb.append(entry.getValue());
            sb.append("\n");
        }

        return sb.toString();
    }
}
