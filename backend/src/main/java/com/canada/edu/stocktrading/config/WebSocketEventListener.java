package com.canada.edu.stocktrading.config;

import com.canada.edu.stocktrading.model.Order;
import com.canada.edu.stocktrading.repository.OrderRepository;
import com.canada.edu.stocktrading.repository.UserRepository;
import com.canada.edu.stocktrading.service.UserService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.*;
import java.util.stream.Collectors;

@Data
@Component
public class WebSocketEventListener {
    private Set<Order> orderSet;

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserService userService;

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {

        StompHeaderAccessor stompAccessor = StompHeaderAccessor.wrap(event.getMessage());
        @SuppressWarnings("rawtypes")
        GenericMessage connectHeader = (GenericMessage) stompAccessor
                .getHeader(SimpMessageHeaderAccessor.CONNECT_MESSAGE_HEADER);
        // to the server
        @SuppressWarnings("unchecked")
        Map<String, List<String>> nativeHeaders = (Map<String, List<String>>) connectHeader.getHeaders()
                .get(SimpMessageHeaderAccessor.NATIVE_HEADERS);

        String userId = nativeHeaders.get("userId").get(0);
        String sessionId = stompAccessor.getSessionId();
        if(this.orderSet==null){
            this.orderSet = new HashSet<>();
        }
        if(userService.isUserIdValid(userId)){
            List<Order>ordersWorkingStatus = orderRepository.getAllOrdersByUserId(userId).stream().filter(o -> o.getOrderStatus().name().equals("WORKING")).collect(Collectors.toList());

            this.orderSet.addAll(ordersWorkingStatus);
        }
    }

//    @EventListener
//    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
//        StompHeaderAccessor stompAccessor = StompHeaderAccessor.wrap(event.getMessage());
//        String sessionId = stompAccessor.getSessionId();
//        Order offlineUsr = this.onlineUsrs
//                .stream()
//                .filter((a)->a.getSessionId().equals(sessionId))
//                .collect(Collectors.toList()).get(0);
//        this.onlineUsrs.remove(offlineUsr);
//    }
}
