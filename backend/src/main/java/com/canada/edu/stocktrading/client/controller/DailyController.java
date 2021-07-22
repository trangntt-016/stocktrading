package com.canada.edu.stocktrading.client.controller;

import com.canada.edu.stocktrading.client.ws.DailyWS;
import com.canada.edu.stocktrading.service.DailyService;
import com.canada.edu.stocktrading.service.dto.DailyDtoWith03MonthSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

@Controller
public class DailyController implements DailyWS {
    private Integer selectedSymbolId;

    private final SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    DailyService dailyService;

    public DailyController(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @MessageMapping("/{symbolId}")
    public String getDaily(Integer symbolId) {
        //update selected symbolId to send schedule
        this.selectedSymbolId = symbolId;
        DailyDtoWith03MonthSummary daily = dailyService.findDailyBySymbolId(symbolId);
        if(daily == null){
            return null;
        }
        return daily.toString();
    }

    @Scheduled(fixedDelay = 10000)
    public void sendScheduledMessage() {
        if(this.selectedSymbolId!=null) {
            DailyDtoWith03MonthSummary dailies = dailyService.findDailyBySymbolId(this.selectedSymbolId);
            if(dailies!=null){
                this.simpMessagingTemplate.convertAndSend("/topic/"+this.selectedSymbolId,dailies.toString());
            }
        }

    }
}
