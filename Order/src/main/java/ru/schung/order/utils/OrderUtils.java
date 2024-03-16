package ru.schung.order.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class OrderUtils {
    public static Date convertStringToDate(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMdd");
        try {
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Date convertToDate(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMdd");
        Date date = null;
        try {
            date = dateFormat.parse(dateString);
            System.out.println("Date: " + date);
        } catch (ParseException e) {
            System.out.println("Invalid date format");
            e.printStackTrace();
        }
        return date;
    }

}
