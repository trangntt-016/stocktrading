package com.canada.edu.stocktrading.scheduler;

import com.canada.edu.stocktrading.service.impl.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {
    @Autowired
    private OrderServiceImpl orderService;

    @Scheduled(cron="0 1 16 * * *")
    public void resetOrderDataAfterMarketCloses() {
        this.orderService.resetOrderData();
    }
}
