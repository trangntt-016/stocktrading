package com.canada.edu.stocktrading.service.utils;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

public class ConvertTimeUtils {
    public static Calendar getXDaysAgo(int days){
        Date in = new Date();
        LocalDateTime ldt = LocalDateTime.ofInstant(in.toInstant(), ZoneId.systemDefault()).minusDays(days);
        Date out = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
        Calendar calc = new Calendar.Builder().setInstant(out).build();
        return calc;
    }

    public static Calendar getToday(){
        Date in = new Date();
        LocalDateTime ldt = LocalDateTime.ofInstant(in.toInstant(), ZoneId.systemDefault());
        Date out = Date.from(ldt.atZone(ZoneId.of("America/Toronto")).toInstant());
        Calendar today = new Calendar.Builder().setInstant(out).build();
        return today;
    }

    public static Timestamp convertCurrentTimeTo14July(){
        LocalDateTime now = LocalDateTime.now();
        int hours = now.getHour();
        int minutes = now.getMinute();
        int seconds = now.getSecond();
        LocalDateTime converted = LocalDateTime.of(2021, Month.JULY,14,hours,minutes,seconds);
        Timestamp ts = Timestamp.valueOf(converted);
        return ts;
    }
}
