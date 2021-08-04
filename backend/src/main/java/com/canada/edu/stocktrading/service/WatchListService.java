package com.canada.edu.stocktrading.service;

import com.canada.edu.stocktrading.dto.DailyDtoDetails;
import com.canada.edu.stocktrading.dto.DailyDtoPriceChange;
import com.canada.edu.stocktrading.dto.WatchListDto;
import com.canada.edu.stocktrading.model.UserEntity;

import java.util.List;

public interface WatchListService {

    List<WatchListDto> getAllByUserId(String userId);

    void updateAWatchlist(WatchListDto watchlistDto);

    void deleteByWatchlistId(Integer watchlistId);

    WatchListDto createAWatchlist(String userId, String watchListName);

    List<DailyDtoDetails> getAllDailiesByWatchListId(Integer watchlistId);

    List<DailyDtoPriceChange> getAllDailyDtoPriceChangeByWatchListId(Integer watchlistId);

    WatchListDto deleteSymbolFromWatchList(int watchlistId, int symbolId);

    Boolean isWatchlistValid(Integer watchlistId);

    void createDefaultWatchlist(UserEntity userId);

}
