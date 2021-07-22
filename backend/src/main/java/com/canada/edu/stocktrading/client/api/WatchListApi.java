package com.canada.edu.stocktrading.client.api;

import com.canada.edu.stocktrading.service.dto.DailyDto;
import com.canada.edu.stocktrading.service.dto.WatchListDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/watchlist")
public interface IWatchListApi {

    @GetMapping
    ResponseEntity<List<WatchListDto>> getAllWatchLists (@RequestParam (required = true) String userId);

    @PutMapping
    void update (@RequestBody WatchListDto watchListDto);

    @DeleteMapping("/{watchlistId}")
    void delete (@PathVariable ("watchlistId") int watchlistId);

    @PostMapping
    ResponseEntity<?> create (@RequestParam String userId, @RequestBody String watchlistName);

    @GetMapping("/{watchlistId}/dailies")
    ResponseEntity<List<DailyDto>> getAllDailiesByWatchListId (@PathVariable Integer watchlistId);

    @DeleteMapping("/{watchlistId}/symbols/{symbolId}")
    ResponseEntity<?> deleteASymbolFromWatchlistId (@PathVariable("watchlistId") int watchlistId, @PathVariable("symbolId") int symbolId);

}
