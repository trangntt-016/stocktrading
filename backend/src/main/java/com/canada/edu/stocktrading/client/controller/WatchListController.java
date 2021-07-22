package com.canada.edu.stocktrading.client.controller;

import com.canada.edu.stocktrading.client.api.WatchListApi;
import com.canada.edu.stocktrading.client.controller.exception.BadRequestException;
import com.canada.edu.stocktrading.client.controller.exception.InternalServerException;
import com.canada.edu.stocktrading.factory.ResponseFactory;
import com.canada.edu.stocktrading.service.DailyService;
import com.canada.edu.stocktrading.service.SymbolService;
import com.canada.edu.stocktrading.service.impl.UserServiceImpl;
import com.canada.edu.stocktrading.service.WatchListService;
import com.canada.edu.stocktrading.service.dto.DailyDetailsDto;
import com.canada.edu.stocktrading.service.dto.WatchListDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/watchlist")
public class WatchListController implements WatchListApi {
    private final ResponseFactory responseFactory;

    @Autowired
    UserServiceImpl userEntityService;

    @Autowired
    WatchListService watchlistService;

    @Autowired
    DailyService dailyService;

    @Autowired
    SymbolService symbolService;

    @Autowired
    public WatchListController(ResponseFactory responseFactory) {
        this.responseFactory = responseFactory;
    }

    @Override
    public ResponseEntity<?> getAllWatchLists(String userId) {
        try {
            List<WatchListDto> watchList = watchlistService.getAllByUserId(userId);

            return responseFactory.success(watchList);
        }
        catch(IllegalArgumentException ex){
            throw new BadRequestException (ex.getMessage());
        }
        catch(Exception ex){
            throw new InternalServerException ("Unable to get all watch lists.");
        }
    }

    @Override
    public ResponseEntity<?> update(WatchListDto watchListDto) {
        try{
            watchlistService.update(watchListDto);
            return responseFactory.updated();
        }
        catch(IllegalArgumentException ex){
            throw new BadRequestException (ex.getMessage());
        }
        catch(Exception ex){
            throw new InternalServerException ("Unable to update a watchlist with id " + watchListDto.getWatchlistId());
        }
    }

    @Override
    public ResponseEntity<?> delete(int watchlistId) {
        try{
            watchlistService.delete(watchlistId);

            return responseFactory.deleted();
        }
        catch (IllegalArgumentException ex){
            throw new BadRequestException (ex.getMessage());
        }
        catch(Exception ex){
            throw new InternalServerException ("Unable to delete watchlist with id " + watchlistId);
        }
    }

    @Override
    public ResponseEntity<?> create(String userId, String watchlistName) {
        try{
            WatchListDto watchlistDto = watchlistService.create (userId, watchlistName);

            return responseFactory.created(watchlistDto);
        }
        catch(IllegalArgumentException ex){
            throw new BadRequestException(ex.getMessage());
        }
        catch(Exception ex){
            throw new InternalServerException("Unable to create new watchlist with name " + watchlistName);
        }
    }

    @Override
    public ResponseEntity<?> getAllDailiesByWatchListId(Integer watchlistId) {
        try{
            List<DailyDetailsDto> dailies = watchlistService.getAllDailiesByWatchListId(watchlistId);

            return responseFactory.success(dailies);
        }
        catch (IllegalArgumentException ex){
            throw new BadRequestException (ex.getMessage());
        }
        catch(Exception ex){
            throw new InternalServerException("Unable to get all dailies.");
        }
    }

    @Override
    public ResponseEntity<?> deleteASymbolFromWatchlistId(int watchlistId, int symbolId) {
        try{
            WatchListDto dto = watchlistService.deleteSymbolFromWatchList (watchlistId, symbolId);

            return responseFactory.success(dto);
        }
        catch (IllegalArgumentException ex){
            throw new BadRequestException (ex.getMessage());
        }
        catch(Exception ex){
            throw new InternalServerException ("Unable to remove symbol with id " + symbolId + " from the database");
        }
    }
}
