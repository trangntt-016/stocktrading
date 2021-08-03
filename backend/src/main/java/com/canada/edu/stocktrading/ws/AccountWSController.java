package com.canada.edu.stocktrading.ws;

import com.canada.edu.stocktrading.model.Account;
import com.canada.edu.stocktrading.service.impl.AccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;


@Controller
public class AccountWSController {
    private String userId;

    private final SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private AccountServiceImpl accountService;

    public AccountWSController(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @MessageMapping("/account")
    public void getPositionByUserIdAndSymbolId(String userId){
        this.userId = userId;
    };

    @Scheduled(fixedDelay = 3000)
    public void sendScheduledAccount() {
        if(this.userId!=null) {
            Account account = this.accountService.getAccountByUserId(this.userId);
            this.simpMessagingTemplate.convertAndSendToUser(this.userId,"/queue/account",account.toString());
        }
    }
}
