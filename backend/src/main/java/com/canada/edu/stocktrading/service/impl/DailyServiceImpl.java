package com.canada.edu.stocktrading.service.impl;

import com.canada.edu.stocktrading.model.Daily;
import com.canada.edu.stocktrading.repository.DailyRepository;
import com.canada.edu.stocktrading.service.DailyService;
import com.canada.edu.stocktrading.service.dto.DailyDetailsDto;
import com.canada.edu.stocktrading.service.dto.DailyDtoWith03MonthSummary;
import com.canada.edu.stocktrading.service.utils.ConvertTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class DailyServiceImpl implements DailyService {
    @Autowired
    private DailyRepository dailyRepository;

    public List<DailyDetailsDto> findAllDailiesBySymbolIds(List<Integer> symbolIds){
        // convert current time to match with the dailies in database 14/7
        Timestamp ts = ConvertTimeUtils.convertCurrentTimeTo14July();

        List<Daily>dailies = dailyRepository.findDailiesBySymbolIds(ts, symbolIds);

        List<DailyDetailsDto>dailyDtos = new ArrayList<>();

        // convert to DailyDto
        dailies.stream().forEach(daily->{
            DailyDetailsDto dailyDto = new DailyDetailsDto(daily);
            dailyDtos.add(dailyDto);
        });
        return dailyDtos;
    }

    public DailyDtoWith03MonthSummary findDailyBySymbolId(Integer symbolId){
        Timestamp ts = ConvertTimeUtils.convertCurrentTimeTo14July();
        // returns 1 result => get the first result
        List<Daily> dailies= dailyRepository.findDailiesBySymbolIds(ts, new ArrayList<Integer>(List.of(symbolId)));
        if(dailies.size()>0){
            return new DailyDtoWith03MonthSummary(dailies.get(0));
        }
        return null;
    }
}
