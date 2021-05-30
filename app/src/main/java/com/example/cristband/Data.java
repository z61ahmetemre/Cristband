package com.example.cristband;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Data {

    float      data;
    long       timestamp;

    public float getData() {
        return data;
    }

    public void setData(float data) {
        this.data = data;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    private String getDate(long time) {
        Date date = new Date(time*1000L); // *1000 is to convert seconds to milliseconds
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm, dd MMM yyyy "); // the format of your date
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+3"));

        return sdf.format(date);
    }

    public String toString() {
        return data + " \t " + getDate(timestamp);
    }
}
