package com.example.cristband;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Data {

    int      data;
    long     timestamp;

    public double getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public double getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    private String getDate(long time) {
        Date date = new Date(time*1000L); // *1000 is to convert seconds to milliseconds
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy "); // the format of your date
        sdf.setTimeZone(TimeZone.getTimeZone("GMT-4"));

        return sdf.format(date);
    }

    public String toString() {
        return data + " \t " + getDate(timestamp);
    }
}
