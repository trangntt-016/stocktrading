package com.canada.edu.stocktrading.client.controller;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import java.util.Locale;

@Controller
public class StockWSController {
    @MessageMapping("/{symbol}")
    //@SubscribeMapping("/questions")
    public String getStocks(@DestinationVariable String symbol){
        System.out.println("hi");
        return symbol;
    }
}
