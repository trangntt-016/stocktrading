package com.canada.edu.stocktrading.utils;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

public class ConvertTimeUtils {
    public static Calendar getXDaysAgo(int days){
        LocalDateTime ldt = convertCurrentTimeTo14July().toLocalDateTime().minusDays(days);
        Date out = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
        Calendar calc = new Calendar.Builder().setInstant(out).build();
        return calc;
    }

    public static Calendar getToday(){
        LocalDateTime ldt = convertCurrentTimeTo14July().toLocalDateTime();
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
