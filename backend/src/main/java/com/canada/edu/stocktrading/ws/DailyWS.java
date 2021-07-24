package com.canada.edu.stocktrading.ws;

import org.springframework.messaging.handler.annotation.DestinationVariable;

public interface DailyWS {
    String getDaily(@DestinationVariable Integer symbolId);

    void sendScheduledMessage();
}
