package com.canada.edu.stocktrading.service;

import com.canada.edu.stocktrading.model.Symbol;
import com.canada.edu.stocktrading.model.UserEntity;
import com.canada.edu.stocktrading.model.Watchlist;
import com.canada.edu.stocktrading.repository.WatchlistRepository;
import com.canada.edu.stocktrading.service.dto.WatchlistDto;
import com.canada.edu.stocktrading.service.utils.MapperUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WatchlistService {
    @Autowired
    private WatchlistRepository watchlistRepository;


    public List<WatchlistDto>findAllByUserId(String userId){
        List<Watchlist>wl = watchlistRepository.findAllByUserId(userId);
        List<WatchlistDto>dtos = MapperUtils.mapperList(wl,WatchlistDto.class);
        return dtos;
    }

    public void updateAWatchlist(WatchlistDto watchlistDto) throws Exception {
        Watchlist wl = watchlistRepository.getById(watchlistDto.getWatchlistId());
        wl.setName(watchlistDto.getName());
        wl.setSymbols(watchlistDto.getSymbols());
        watchlistRepository.save(wl);
    }

    public void deleteAWatchlist(Integer watchlistId) throws Exception {
        Watchlist wl = watchlistRepository.getById(watchlistId);
        watchlistRepository.delete(wl);
    }

    public WatchlistDto createAWatchlist(UserEntity user, String watchlistName){
        Watchlist wl = Watchlist.builder()
                .name(watchlistName)
                .user(user)
                .build();
        watchlistRepository.save(wl);
        return MapperUtils.mapperObject(wl, WatchlistDto.class);
    }

    public WatchlistDto findByWatchlistId(Integer watchlistId) throws Exception {
        Optional<Watchlist> wl = watchlistRepository.findById(watchlistId);
        if(wl.isEmpty()){
            throw new Exception("Found nothing with this watchlist Id "+watchlistId);
        }
        return MapperUtils.mapperObject(wl.get(),WatchlistDto.class);
    }

    public WatchlistDto deleteASymbolFromWatchlistId(int watchlistId, Symbol symbol) throws Exception {
        Optional<Watchlist> wl = watchlistRepository.findById(watchlistId);
        if(wl.isEmpty()){
            throw new Exception("Found nothing with this watchlist Id "+watchlistId);
        }
        wl.get().getSymbols().remove(symbol);
        watchlistRepository.save(wl.get());
        WatchlistDto dto = MapperUtils.mapperObject(wl.get(),WatchlistDto.class);
        return dto;
    }
}
