package com.canada.edu.stocktrading.client.api;

import com.canada.edu.stocktrading.service.dto.WatchlistDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface WatchlistApi {

    ResponseEntity<?> getAllWatchList(@RequestParam String userId);

    void updateAWatchlist(@RequestBody WatchlistDto watchlistDto);

    void deleteAWatchlist(@PathVariable("watchlistId")  int watchlistId);

    ResponseEntity<?> createAWatchlist(@RequestParam String userId, @RequestBody String watchlistName);

    ResponseEntity<?> getAllDailiesFromWatchlistId(@PathVariable Integer watchlistId);

    ResponseEntity<?> deleteASymbolFromWatchlistId(@PathVariable("watchlistId")  int watchlistId, @PathVariable("symbolId") int symbolId);

}
