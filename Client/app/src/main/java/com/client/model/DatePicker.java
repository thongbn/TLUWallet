package com.client.model;

/**
 * Created by ToanNguyen on 14/04/2016.
 */
public class DatePicker {

    public DatePicker () {
        super ();
    }

    public static String getDate() {
        return date;
    }

    public static void setDate(String date) {
        DatePicker.date = date;
    }

    private static String date;
}
