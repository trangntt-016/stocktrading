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

import java.util.List;

@Controller
public class PositionWSController {
    private final SimpMessagingTemplate simpMessagingTemplate;

    private String selectedUserId;

    @Autowired
    private PositionServiceImpl positionService;


    public PositionWSController (SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @MessageMapping("/position")
    public void getPositionByUserIdAndSymbolId(String userId){
        try{
            this.selectedUserId = userId;
            List<PositionDto> positionDtos = positionService.getOrderedPositions(this.selectedUserId);
            this.simpMessagingTemplate.convertAndSendToUser(this.selectedUserId,"/queue/position",positionDtos.toString());
        }
        catch (IllegalArgumentException ex){
            throw new BadRequestException(ex.getMessage());
        }
        catch (RuntimeException ex) {
            throw new InternalServerException(ex.getMessage());
        }
    };

    @Scheduled(fixedDelay = 1000)
    public void sendScheduledPositionByUserIdAndSymbolId(){
        if(this.selectedUserId != null) {
            try{
                List<PositionDto> positionDtos = positionService.getOrderedPositions(this.selectedUserId);
                this.simpMessagingTemplate.convertAndSendToUser(this.selectedUserId,"/queue/position",positionDtos.toString());
            }
            catch (IllegalArgumentException ex){
                throw new BadRequestException(ex.getMessage());
            }
            catch (RuntimeException ex) {
                throw new InternalServerException(ex.getMessage());
            }
        }
    };
}
