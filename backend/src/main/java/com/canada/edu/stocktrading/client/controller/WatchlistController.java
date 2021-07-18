package com.canada.edu.stocktrading.client.controller;

import com.canada.edu.stocktrading.client.controller.exception.BadRequestException;
import com.canada.edu.stocktrading.model.Daily;
import com.canada.edu.stocktrading.model.Symbol;
import com.canada.edu.stocktrading.model.UserEntity;
import com.canada.edu.stocktrading.repository.DailyRepository;
import com.canada.edu.stocktrading.service.DailyService;
import com.canada.edu.stocktrading.service.SymbolService;
import com.canada.edu.stocktrading.service.UserEntityService;
import com.canada.edu.stocktrading.service.WatchlistService;
import com.canada.edu.stocktrading.service.dto.DailyDto;
import com.canada.edu.stocktrading.service.dto.WatchlistDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/watchlists")
public class WatchlistController {
    @Autowired
    UserEntityService userEntityService;

    @Autowired
    WatchlistService watchlistService;

    @Autowired
    DailyService dailyService;

    @Autowired
    SymbolService symbolService;

    @GetMapping
    public ResponseEntity<List<WatchlistDto>>getAllWatchlists(
            @RequestParam(required = true) String userId)
    {
        // check if userId exists
        boolean isValid = userEntityService.isUserIdValid(userId);
        if(!isValid){
            throw new BadRequestException("Found nothing with this userId "+userId);
        }
        List<WatchlistDto>watchlistDto = watchlistService.findAllByUserId(userId);
        return new ResponseEntity<List<WatchlistDto>>(watchlistDto, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Void> updateAWatchlist(@RequestBody WatchlistDto watchlistDto){
        try{
            watchlistService.updateAWatchlist(watchlistDto);
        }
        catch(Exception ex){
            throw new BadRequestException("Unable to find watchlist with id "+watchlistDto.getWatchlistId());
        }
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{watchlistId}")
    public ResponseEntity<Void> deleteAWatchlist(@PathVariable("watchlistId")  int watchlistId){
        try{
            watchlistService.deleteAWatchlist(watchlistId);
        }
        catch(Exception ex){
            throw new BadRequestException("Unable to find watchlist with id "+watchlistId);
        }
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<WatchlistDto>createAWatchlist(
            @RequestParam(required = true) String userId,
            @RequestBody String watchlistName){
        WatchlistDto watchlistDto = null;
        try{
            UserEntity user = userEntityService.findByUserId(userId);
            watchlistDto = watchlistService.createAWatchlist(user, watchlistName);
        }
        catch(Exception ex){
            throw new BadRequestException(ex.getMessage());
        }
        return new ResponseEntity<WatchlistDto>(watchlistDto,HttpStatus.OK);
    }

    @GetMapping("/{watchlistId}/dailies")
    public ResponseEntity<List<DailyDto>> getAllDailiesFromWatchlistId(@PathVariable Integer watchlistId)
    {
        // check if watchlistId exists
        WatchlistDto wl = null;
        try{
            wl = watchlistService.findByWatchlistId(watchlistId);
        }
        catch(Exception ex){
            throw new BadRequestException(ex.getMessage());
        }
        if(wl.getSymbols().size()==0){
            return ResponseEntity.ok(null);
        }
        List<Integer>symbolIds = wl.getSymbols().stream().map(Symbol::getSymbolId).collect(Collectors.toList());
        List<DailyDto>dailies = dailyService.findAllDailiesBySymbolIds(symbolIds);
        return ResponseEntity.ok(dailies);
    }

    @DeleteMapping("/{watchlistId}/symbols/{symbolId}")
    public ResponseEntity<WatchlistDto> deleteASymbolFromWatchlistId(
            @PathVariable("watchlistId")  int watchlistId,
            @PathVariable("symbolId") int symbolId){
        Symbol symbol = null;
        WatchlistDto watchlistDto = null;
        try{
            symbol = symbolService.findBySymbolId(symbolId);
        }
        catch(Exception ex){
            throw new BadRequestException(ex.getMessage());
        }
        try{
            watchlistDto = watchlistService.deleteASymbolFromWatchlistId(watchlistId,symbol);
        }
        catch(Exception ex){
            throw new BadRequestException(ex.getMessage());
        }
        return ResponseEntity.ok(watchlistDto);
    }

}
