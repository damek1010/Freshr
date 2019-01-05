package com.company;

public enum Parameter {
    ST("st"), SO2("SO2"), NO2("NO2"), CO("CO"), PM10("PM10"), PM25("PM25"), O3("O3"), C6H6("C6H6");
    private String name;
    Parameter(String name) {
        this.name = name;
    }

    public String getName() {
        return name.toUpperCase();
    }

    public static boolean contains(String name) {
        boolean result = false;
        for (Parameter param : Parameter.values()) {
            if (param.getName().equals(name.toUpperCase())) {
                result = true;
                break;
            }
        }
        return result;
    }
}
