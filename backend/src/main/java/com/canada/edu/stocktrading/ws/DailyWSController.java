package com.canada.edu.stocktrading.ws;

import com.canada.edu.stocktrading.dto.DailyDtoBidAsk;
import com.canada.edu.stocktrading.dto.WsSelectedDto;
import com.canada.edu.stocktrading.service.DailyService;
import com.canada.edu.stocktrading.dto.DailyDto03MSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Controller
public class DailyWSController {
    private Integer selectedSymbolId;

    private Map<String, Integer> selectedUserIdSymbolIdBidAskMap;

    private final SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    DailyService dailyService;

    public DailyWSController(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @MessageMapping("/{symbolId}")
    public String getDaily(@DestinationVariable Integer symbolId) {
        //update selected symbolId to send schedule
        this.selectedSymbolId = symbolId;
        DailyDto03MSummary daily = dailyService.findDailyBySymbolId(symbolId);
        if(daily == null){
            return null;
        }
        return daily.toString();
    }

    @Scheduled(fixedDelay = 3000)
    public void sendScheduledMessage() {
        if(this.selectedSymbolId!=null) {
            DailyDto03MSummary dailies = dailyService.findDailyBySymbolId(this.selectedSymbolId);
            if(dailies!=null){
                this.simpMessagingTemplate.convertAndSend("/topic/"+this.selectedSymbolId,dailies.toString());
            }
        }
    }

    @MessageMapping("/bid-ask")
    public void getDailyWithBidAsk(@Payload String selectedUserSymbolDto) {
        String userId = selectedUserSymbolDto.split(",")[0].split(":")[1];
        Integer symbolId = Integer.valueOf(selectedUserSymbolDto.split(",")[1].split(":")[1]);

        //update selected symbolId to send schedule
        if(this.selectedUserIdSymbolIdBidAskMap == null) this.selectedUserIdSymbolIdBidAskMap = new HashMap<>();

        this.selectedUserIdSymbolIdBidAskMap.put(userId, symbolId);

    }

    @Scheduled(fixedDelay = 3000)
    public void sendScheduledDailyBidAsk() {
        if(this.selectedUserIdSymbolIdBidAskMap!=null){
            for (String key : this.selectedUserIdSymbolIdBidAskMap.keySet()) {
                Integer symbolId = this.selectedUserIdSymbolIdBidAskMap.get(key);
                DailyDtoBidAsk dailyBidAskDto = dailyService.getDailyBidAskBySymbolId(symbolId);
                if(dailyBidAskDto!=null){
                    this.simpMessagingTemplate.convertAndSendToUser(key,"/topic/bid-ask",dailyBidAskDto.toString());
                }
            }
        }
    }

    @Scheduled(fixedDelay = 15000)
    public void sendScheduledFuturePrice() {
        if(this.selectedUserIdSymbolIdBidAskMap!=null){
            for (String userId : this.selectedUserIdSymbolIdBidAskMap.keySet()) {
                Integer symbolId = this.selectedUserIdSymbolIdBidAskMap.get(userId);
                DailyDtoBidAsk dailyBidAskDto = dailyService.getDailyBidAskBySymbolId(symbolId);
                if(dailyBidAskDto!=null){
                    BigDecimal matchedPrice = dailyService.getMatchedPriceInNext15sBySymbolId(symbolId);
                    this.simpMessagingTemplate.convertAndSendToUser(userId, "/topic/matched-price", matchedPrice);
                }
            }
        }
    }
}
