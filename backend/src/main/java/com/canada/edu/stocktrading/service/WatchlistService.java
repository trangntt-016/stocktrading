package com.canada.edu.stocktrading.service;

import com.canada.edu.stocktrading.model.Watchlist;
import com.canada.edu.stocktrading.repository.WatchlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WatchlistService {
    @Autowired
    private WatchlistRepository watchlistRepository;

    public List<Watchlist>findAllByUserId(String userId){
        return watchlistRepository.findAllByUserId(userId);
    }
}
