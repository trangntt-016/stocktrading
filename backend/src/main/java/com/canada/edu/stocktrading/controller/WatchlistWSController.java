package com.canada.edu.stocktrading.controller;

import com.canada.edu.stocktrading.dto.DailyDtoPriceChange;
import com.canada.edu.stocktrading.model.Daily;
import com.canada.edu.stocktrading.service.WatchListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class WatchlistWSController {
    private Integer selectedWatchlistId;

    private String watchlistUserId;

    private final SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    WatchListService watchListService;


    public WatchlistWSController(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @MessageMapping("/watchlist/{watchlistId}")
    public void getDailyWithBidAsk(@DestinationVariable Integer watchlistId) {
        //update selected symbolId to send schedule
        if(watchListService.isWatchlistValid(watchlistId)) {
            this.selectedWatchlistId = watchlistId;
            this.watchlistUserId = this.watchListService.getUserIdByWatchlistId(watchlistId);
        }
    }

    @Scheduled(fixedDelay = 1000)
    public void sendScheduledDailiestoAWatchlist() {
        try{
            if(this.selectedWatchlistId != null && this.selectedWatchlistId !=null){
                List<DailyDtoPriceChange> dailies = watchListService.getAllDailyDtoPriceChangeByWatchListId(this.selectedWatchlistId);
                if(dailies != null){
                    this.simpMessagingTemplate.convertAndSendToUser(this.watchlistUserId,"/queue/watchlist/"+this.selectedWatchlistId,dailies.toString());
                }
                else{
                    this.simpMessagingTemplate.convertAndSendToUser(this.watchlistUserId,"/queue/watchlist/"+this.selectedWatchlistId,"null");
                }
            }
        }
        catch(IllegalArgumentException ex){
            ex.printStackTrace();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }

    }
}
