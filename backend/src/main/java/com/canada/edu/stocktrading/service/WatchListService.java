package com.canada.edu.stocktrading.service;

import com.canada.edu.stocktrading.service.dto.DailyDetailsDto;
import com.canada.edu.stocktrading.service.dto.WatchListDto;

import java.util.List;

public interface WatchListService {

    List<WatchListDto> getAllByUserId(String userId);

    void update(WatchListDto watchlistDto);

    void delete(Integer watchlistId);

    WatchListDto create(String userId, String watchListName);

    List<DailyDetailsDto> getAllDailiesByWatchListId(Integer watchlistId);

    WatchListDto deleteSymbolFromWatchList(int watchlistId, int symbolId);

}
