package com.company;

import java.util.Date;
import java.util.HashMap;

public class Sensor {
    private Integer id;
    private Integer stationId;
    private Parameter parameter;
    private HashMap<Date, Double> values;

    public Sensor(Integer sensorId, Integer stationId, Parameter parameter) {
        this.id = sensorId;
        this.stationId = stationId;
        this.parameter = parameter;
    }

    @Override
    public String toString() {
        return String.format("%s: %s", stationId, parameter);
    }

    public Integer getID() {
        return id;
    }

    public void setValues(HashMap<Date, Double> values) {
        this.values = values;
    }

    public Double getValue(Date date) {
        return this.values.getOrDefault(date, 0.0);
    }

    public Parameter getParameter() {
        return this.parameter;
    }
}
