package com.canada.edu.stocktrading.ws;

import com.canada.edu.stocktrading.model.Account;
import com.canada.edu.stocktrading.service.impl.AccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import java.util.HashSet;
import java.util.Set;


@Controller
public class AccountWSController {
    Set<String> userIdsSet;

    private final SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private AccountServiceImpl accountService;

    public AccountWSController(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @MessageMapping("/account")
    public void getPositionByUserIdAndSymbolId(String userId){
        if(this.userIdsSet == null ) this.userIdsSet = new HashSet<>();

        this.userIdsSet.add(userId);

    };

    @Scheduled(fixedDelay = 3000)
    public void sendScheduledAccount() {
        if(this.userIdsSet != null) {
            this.userIdsSet.forEach(id ->{
                Account account = this.accountService.getAccountByUserId(id);
                this.simpMessagingTemplate.convertAndSendToUser(id,"/queue/account",account.toString());
            });
        }
    }
}
