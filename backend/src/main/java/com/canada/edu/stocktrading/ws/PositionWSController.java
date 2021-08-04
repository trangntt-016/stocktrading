package com.canada.edu.stocktrading.ws;

import com.canada.edu.stocktrading.api.exception.BadRequestException;
import com.canada.edu.stocktrading.api.exception.InternalServerException;
import com.canada.edu.stocktrading.dto.PositionDto;
import com.canada.edu.stocktrading.service.impl.PositionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class PositionWSController {
    private Set<String> selectedUserSet;

    private final SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private PositionServiceImpl positionService;


    public PositionWSController (SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @MessageMapping("/position")
    public void getPositionByUserIdAndSymbolId(String userId){
        if(this.selectedUserSet == null) this.selectedUserSet = new HashSet<>();

        this.selectedUserSet.add(userId);
    };

    @Scheduled(fixedDelay = 1000)
    public void sendScheduledPositionByUserIdAndSymbolId(){
        if(this.selectedUserSet != null) {
            this.selectedUserSet.forEach(userId ->{
                try{
                    List<PositionDto> positionDtos = positionService.getOrderedPositions(userId);
                    this.simpMessagingTemplate.convertAndSendToUser(userId,"/queue/position",positionDtos.toString());
                }
                catch (IllegalArgumentException ex){
                    throw new BadRequestException(ex.getMessage());
                }
                catch (RuntimeException ex) {
                    throw new InternalServerException(ex.getMessage());
                }
            });
        }
    };
}
