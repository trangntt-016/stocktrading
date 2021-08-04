package com.canada.edu.stocktrading.service.impl;

import com.canada.edu.stocktrading.dto.DailyDtoPriceChange;
import com.canada.edu.stocktrading.model.Daily;
import com.canada.edu.stocktrading.model.Symbol;
import com.canada.edu.stocktrading.model.UserEntity;
import com.canada.edu.stocktrading.model.WatchList;
import com.canada.edu.stocktrading.repository.DailyRepository;
import com.canada.edu.stocktrading.repository.WatchlistRepository;
import com.canada.edu.stocktrading.service.DailyService;
import com.canada.edu.stocktrading.service.SymbolService;
import com.canada.edu.stocktrading.service.WatchListService;
import com.canada.edu.stocktrading.dto.DailyDtoDetails;
import com.canada.edu.stocktrading.dto.WatchListDto;
import com.canada.edu.stocktrading.utils.ConvertTimeUtils;
import com.canada.edu.stocktrading.utils.MapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class WatchListServiceImpl implements WatchListService {

    @Autowired
    private WatchlistRepository watchlistRepository;

    @Autowired
    private DailyRepository dailyRepository;

    @Autowired
    private UserEntityServiceImpl userEntityService;

    @Autowired
    private DailyService dailyService;

    @Autowired
    private SymbolService symbolService;

    @Override
    public List<WatchListDto> getAllByUserId(String userId) {
        boolean isValidUserId = userEntityService.isUserIdValid(userId);

        if (isValidUserId) {
            List<WatchList> watchList = watchlistRepository.findAllByUserId(userId);

            return MapperUtils.mapperList(watchList, WatchListDto.class);

        } else {
            throw new IllegalArgumentException("Unable to find user with id " + userId);
        }
    }

    @Override
    public void updateAWatchlist(WatchListDto watchlistDto) {
        Optional<WatchList> watchList = watchlistRepository.findById(watchlistDto.getWatchlistId());

        if (watchList.isEmpty()){
            throw new IllegalArgumentException("Unable to find watchlist with id " + watchlistDto.getWatchlistId());
        }

        watchList.get().setName(watchlistDto.getName());

        watchList.get().setSymbols(watchlistDto.getSymbols());

        watchlistRepository.save(watchList.get());
    }

    @Override
    public void deleteByWatchlistId(Integer watchlistId) {
        Optional<WatchList> watchList = watchlistRepository.findById(watchlistId);

        if (watchList.isEmpty()){
            throw new IllegalArgumentException("Unable to find watchlist with id " + watchlistId);
        }

        watchlistRepository.delete(watchList.get());
    }

    @Override
    public WatchListDto createAWatchlist(String userId, String watchListName){
        try{
            UserEntity user = userEntityService.getUserByUserId(userId);

            WatchList wl = WatchList.builder()
                    .name(watchListName)
                    .user(user)
                    .build();

            watchlistRepository.save(wl);

            return MapperUtils.mapperObject(wl, WatchListDto.class);
        }
        catch(IllegalArgumentException ex){
            throw new IllegalArgumentException(ex.getMessage());
        }
    }

    @Override
    public List<DailyDtoDetails> getAllDailiesByWatchListId(Integer watchlistId) {
        Optional<WatchList> watchlist = watchlistRepository.findById(watchlistId);

        if(watchlist.isEmpty()){
            throw new IllegalArgumentException("Unable to find watchlist with id "+ watchlistId);
        }

        if(watchlist.get().getSymbols().size() == 0){
            return null;
        }

        List<Integer>symbolIds = watchlist.get().getSymbols().stream().map(Symbol::getSymbolId).collect(Collectors.toList());

        return dailyService.findAllDailiesBySymbolIds(symbolIds);
    }

    @Override
    public List<DailyDtoPriceChange> getAllDailyDtoPriceChangeByWatchListId(Integer watchlistId){
        if(!isWatchlistValid(watchlistId)) throw new IllegalArgumentException("Unable to find watchlist with id " + watchlistId);

        Optional<WatchList> watchlist = watchlistRepository.findById(watchlistId);

        if(watchlist.get().getSymbols().size() == 0) return null;

        Timestamp ts = ConvertTimeUtils.convertCurrentTimeTo14July();

        List<Integer>symbolIds = watchlist.get().getSymbols().stream().map(Symbol::getSymbolId).collect(Collectors.toList());

        List<Daily> dailies = dailyRepository.findDailiesBySymbolIds(ts, symbolIds);

        List<DailyDtoPriceChange> dtos = new ArrayList<>();

        for (Daily daily : dailies) {
            DailyDtoPriceChange dto = new DailyDtoPriceChange(daily);

            dtos.add(dto);
        }

        return dtos;
    }

    @Override
    public WatchListDto deleteSymbolFromWatchList(int watchlistId, int symbolId) {
        try{
            Symbol symbol = symbolService.getOneBySymbolId(symbolId);

            Optional<WatchList> watchlist = watchlistRepository.findById(watchlistId);

            if(watchlist.isEmpty()){
                throw new IllegalStateException("Unable to find watchlist with id "+ watchlistId);
            }
            watchlistRepository.deleteSymbolFromWatchList(symbolId);

            watchlist.get().getSymbols().remove(symbol);

            return MapperUtils.mapperObject(watchlist.get(),WatchListDto.class);

        }
        catch(IllegalArgumentException ex){
            throw new IllegalStateException(ex.getMessage());
        }
    }

    @Override
    public Boolean isWatchlistValid(Integer watchlistId) {
        Optional<WatchList> watchlist = watchlistRepository.findById(watchlistId);

        if(watchlist.isEmpty()){
            return false;
        }
        return true;
    }


    @Override
    public void createDefaultWatchlist(UserEntity user) {
        Set<Symbol> randomSymbols = symbolService.getRandom05Symbols();


        WatchList defaulWatchlist = WatchList.builder()
                .name("default")
                .user(user)
                .symbols(randomSymbols)
                .build();

        watchlistRepository.save(defaulWatchlist);
    }
}
