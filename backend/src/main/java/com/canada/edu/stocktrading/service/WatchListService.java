package com.canada.edu.stocktrading.service;

import com.canada.edu.stocktrading.service.dto.DailyDto;
import com.canada.edu.stocktrading.service.dto.WatchListDto;

import java.util.List;

public interface IWatchListService {

    List<WatchListDto> getAllByUserId(String userId);

    void update(WatchListDto watchlistDto);

    void delete(Integer watchlistId);

    WatchListDto create(String userId, String watchListName);

    List<DailyDto> getAllDailiesByWatchListId(Integer watchlistId);

    WatchListDto deleteSymbolFromWatchList(int watchlistId, int symbolId);

}
