package com.canada.edu.stocktrading.ws;

import com.canada.edu.stocktrading.dto.DailyDtoPriceChange;
import com.canada.edu.stocktrading.service.WatchListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class WatchlistWSController {
    Map<String, Integer> userWatchlistMap;

    private final SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    WatchListService watchListService;


    public WatchlistWSController(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @MessageMapping("/watchlist")
    public void getDailyWithBidAsk(@Payload String selectedUserWatchlist) {
        String userId = selectedUserWatchlist.split(",")[0].split(":")[1];
        Integer watchlistId = Integer.valueOf(selectedUserWatchlist.split(",")[1].split(":")[1]);

        //update selected symbolId to send schedule
        if(this.userWatchlistMap == null) {
            this.userWatchlistMap = new HashMap<>();
        }
        this.userWatchlistMap.put(userId, watchlistId);
    }

    @Scheduled(fixedDelay = 1000)
    public void sendScheduledDailiesToAWatchlist() {
        try{
            if(this.userWatchlistMap != null){
                this.userWatchlistMap.forEach((userId,watchlistId) -> {
                    List<DailyDtoPriceChange> dailies = watchListService.getAllDailyDtoPriceChangeByWatchListId(watchlistId);
                    if(dailies != null){
                        this.simpMessagingTemplate.convertAndSendToUser(userId,"/queue/watchlist/"+watchlistId,dailies.toString());
                    }
                    else{
                        this.simpMessagingTemplate.convertAndSendToUser(userId,"/queue/watchlist/"+watchlistId,"null");
                    }
                });
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
