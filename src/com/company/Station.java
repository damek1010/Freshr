package com.company;

import com.google.gson.annotations.SerializedName;

/**
 * Class representing every single station record
 */
public class Station {
    /**
     * Station ID
     */
    private Integer id;
    /**
     * Station Name
     */
    @SerializedName("stationName")
    private String name;

    public String getName() {
        return name;
    }

    public Integer getID() {
        return id;
    }

    @Override
    public String toString() {
        return String.format("%d: %s", this.id, this.name);
    }
}
