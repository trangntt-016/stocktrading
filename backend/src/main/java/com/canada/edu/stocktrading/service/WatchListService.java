package com.canada.edu.stocktrading.service;

import com.canada.edu.stocktrading.dto.DailyDtoDetails;
import com.canada.edu.stocktrading.dto.DailyDtoPriceChange;
import com.canada.edu.stocktrading.dto.WatchListDto;
import com.canada.edu.stocktrading.model.Daily;

import java.util.List;

public interface WatchListService {

    List<WatchListDto> getAllByUserId(String userId);

    void update(WatchListDto watchlistDto);

    void delete(Integer watchlistId);

    WatchListDto create(String userId, String watchListName);

    List<DailyDtoDetails> getAllDailiesByWatchListId(Integer watchlistId);

    List<DailyDtoPriceChange> getAllDailyDtoPriceChangeByWatchListId(Integer watchlistId);

    WatchListDto deleteSymbolFromWatchList(int watchlistId, int symbolId);

    Boolean isWatchlistValid(Integer watchlistId);

    String getUserIdByWatchlistId(Integer watchlistId);

}
