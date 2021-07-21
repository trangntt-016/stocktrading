package com.canada.edu.stocktrading.service;

import com.canada.edu.stocktrading.service.dto.WatchListDto;

import java.util.List;

public interface WatchlistService {

    List<WatchListDto> getAllByUserId(String userId);

    void updateAWatchlist(WatchListDto watchlistDto);

    void deleteAWatchlist(Integer watchlistId);

}
