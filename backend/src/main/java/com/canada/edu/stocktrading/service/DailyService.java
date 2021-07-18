package com.canada.edu.stocktrading.service;

import com.canada.edu.stocktrading.model.Daily;
import com.canada.edu.stocktrading.model.Symbol;
import com.canada.edu.stocktrading.repository.DailyRepository;
import com.canada.edu.stocktrading.service.dto.DailyDto;
import com.canada.edu.stocktrading.service.utils.MapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

@Service
public class DailyService {
    @Autowired
    private DailyRepository dailyRepository;

    public List<DailyDto> findAllDailiesBySymbolIds(List<Integer> symbolIds){
        // convert current time to match with the dailies in database 14/7
        LocalDateTime now = LocalDateTime.now();
        int hours = now.getHour();
        int minutes = now.getMinute();
        int seconds = now.getSecond();
        LocalDateTime converted = LocalDateTime.of(2021, Month.JULY,14,hours,minutes,seconds);
        Timestamp ts = Timestamp.valueOf(converted);

        List<Daily>dailies = dailyRepository.findDailiesBySymbolIds(ts, symbolIds);
        List<DailyDto>dailyDtos = new ArrayList<>();

        // convert to DailyDto
        dailies.stream().forEach(daily->{
           BigDecimal regularPrice = daily.getPrice();
           BigDecimal change = regularPrice.subtract(daily.getPrevClose());
           BigDecimal changeInPercent = change.multiply(new BigDecimal(100)).divide(regularPrice, RoundingMode.HALF_UP);

            DailyDto dailyDto = MapperUtils.mapperObject(daily,DailyDto.class);
            dailyDto.setChange(change);
            dailyDto.setChangeInPercent(changeInPercent);
            dailyDtos.add(dailyDto);
        });
        return dailyDtos;
    }
}
