package com.canada.edu.stocktrading.service.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

public class ConvertCalendarUtils {
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
        Date out = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
        Calendar today = new Calendar.Builder().setInstant(out).build();
        return today;
    }
}
