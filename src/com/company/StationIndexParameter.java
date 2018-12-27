package com.company;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * StationIndexParameter represents single parameter in StationIndex
 */
public class StationIndexParameter {
    private Date calcDate;
    private Date sourceDate;
    private String indexLevelName;

    StationIndexParameter(String calcDate, String sourceDate, String indexLevelName) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            if (!calcDate.isEmpty()) {
                if (calcDate.contains(" ")) {
                    this.calcDate = sdf.parse(calcDate);
                } else {
                    long millis = Long.parseLong(calcDate);
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(millis);
                    this.calcDate = calendar.getTime();
                }
            }

            if (!sourceDate.isEmpty()) {
                if (sourceDate.contains(" ")) {
                    this.sourceDate = sdf.parse(sourceDate);
                } else {
                    long millis = Long.parseLong(sourceDate);
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(millis);
                    this.sourceDate = calendar.getTime();
                }
            }
            this.indexLevelName = indexLevelName;
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.calcDate);
        sb.append("\n");
        sb.append(this.sourceDate);
        sb.append("\n");
        sb.append(this.indexLevelName);
        sb.append("\n");
        return sb.toString();
    }
}