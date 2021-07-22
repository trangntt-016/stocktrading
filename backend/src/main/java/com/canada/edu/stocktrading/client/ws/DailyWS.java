package com.canada.edu.stocktrading.client.ws;

import org.springframework.messaging.handler.annotation.DestinationVariable;

public interface DailyWS {
    String getDaily(@DestinationVariable Integer symbolId);

    void sendScheduledMessage();
}
