package com.canada.edu.stocktrading.client.controller;

import com.canada.edu.stocktrading.client.controller.exception.BadRequestException;
import com.canada.edu.stocktrading.model.UserEntity;
import com.canada.edu.stocktrading.model.Watchlist;
import com.canada.edu.stocktrading.service.UserEntityService;
import com.canada.edu.stocktrading.service.WatchlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/watchlists")
public class WatchlistController {
    @Autowired
    UserEntityService userEntityService;

    @Autowired
    WatchlistService watchlistService;

    @GetMapping
    public ResponseEntity<List<Watchlist>>getAllWatchlists(
            @RequestParam(required = true) String userId)
    {
        // check if userId exists
        boolean isValid = userEntityService.isUserIdValid(userId);
        if(!isValid){
            throw new BadRequestException("Found nothing with this userId "+userId);
        }
        List<Watchlist>found = watchlistService.findAllByUserId(userId);
        return new ResponseEntity<List<Watchlist>>(found, HttpStatus.OK);
    }
}
