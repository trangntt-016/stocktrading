package com.canada.edu.stocktrading.controller;

import com.canada.edu.stocktrading.service.DailyService;
import com.canada.edu.stocktrading.dto.DailyDto03MSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

@Controller
public class DailyController {
    private Integer selectedSymbolId;

    private final SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    DailyService dailyService;

    public DailyController(SimpMessagingTemplate simpMessagingTemplate) {
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

    @Scheduled(fixedDelay = 10000)
    public void sendScheduledMessage() {
        if(this.selectedSymbolId!=null) {
            DailyDto03MSummary dailies = dailyService.findDailyBySymbolId(this.selectedSymbolId);
            if(dailies!=null){
                this.simpMessagingTemplate.convertAndSend("/topic/"+this.selectedSymbolId,dailies.toString());
            }
        }

    }
}
