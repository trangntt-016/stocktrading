package com.canada.edu.stocktrading.service.impl;

import com.canada.edu.stocktrading.dto.PositionDto;
import com.canada.edu.stocktrading.model.Daily;
import com.canada.edu.stocktrading.model.OrderSide;
import com.canada.edu.stocktrading.model.OrderStatus;
import com.canada.edu.stocktrading.model.Symbol;
import com.canada.edu.stocktrading.repository.DailyRepository;
import com.canada.edu.stocktrading.repository.OrderRepository;
import com.canada.edu.stocktrading.repository.SymbolRepository;
import com.canada.edu.stocktrading.service.PositionService;
import com.canada.edu.stocktrading.utils.ConvertTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PositionServiceImpl implements PositionService {
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    DailyRepository dailyRepository;

    @Autowired
    SymbolRepository symbolRepository;

    @Autowired
    UserEntityServiceImpl userService;

    public Integer calcQuantity(Integer symbolId, String userId){
            Integer noOfBuyOrders = orderRepository.calcNumberOfOrders(OrderStatus.FILLED, OrderSide.BUY, symbolId, userId);

            noOfBuyOrders = (noOfBuyOrders == null) ? 0 : noOfBuyOrders;

            Integer noOfSellOrders = orderRepository.calcNumberOfOrders(OrderStatus.FILLED, OrderSide.SELL, symbolId, userId);

            noOfSellOrders = (noOfSellOrders == null) ? 0 : noOfSellOrders;

            return noOfBuyOrders - noOfSellOrders;

    }

    public BigDecimal calcMarketValue(Integer quantity, BigDecimal lastPrice){
        return lastPrice.multiply(new BigDecimal(quantity));
    }

    public BigDecimal getLastPrice(Symbol symbol){
        Daily found = getDaily(symbol);

        return found.getPrice();
    }


    public BigDecimal calcTotalCost(Integer symbolId, String userId){
        BigDecimal buy = orderRepository.calcTotalAmountOfOrdersBySymbolUserSide("BUY", symbolId, userId);

        buy = (buy == null) ? new BigDecimal(0) : buy;

        BigDecimal sell = orderRepository.calcTotalAmountOfOrdersBySymbolUserSide("SELL", symbolId, userId);

        sell = (sell == null) ? new BigDecimal(0) : sell;

        return buy.subtract(sell);
    }

    public BigDecimal getAvgPrice(BigDecimal totalCost, Integer totalFilledQuantity) {

        return totalCost.divide(new BigDecimal(totalFilledQuantity),RoundingMode.HALF_UP);
    }

    public Daily getDaily (Symbol symbol) {
        Timestamp ts = ConvertTimeUtils.convertCurrentTimeTo14July();

        return dailyRepository.findCurrentDailyBySymbolId(ts.toLocalDateTime().getHour(), ts.toLocalDateTime().getMinute(), ts.toLocalDateTime().getSecond(), symbol.getSymbolId());
    }

    public PositionDto getAPosition(Integer symbolId, String userId) {
        Optional<Symbol> symbol = symbolRepository.findById(symbolId);
        if(symbol.isEmpty()) {
            throw new IllegalArgumentException("Unable to find symbol with id " + symbolId);
        }
        BigDecimal lastPrice = getLastPrice(symbol.get());

        Integer filledQuantity = calcQuantity(symbolId, userId);

        BigDecimal marketValue = calcMarketValue(filledQuantity, lastPrice);

        BigDecimal totalCost = calcTotalCost(symbolId, userId);

        BigDecimal avgPrice = getAvgPrice(totalCost, filledQuantity);

        BigDecimal unrealizedPL = marketValue.subtract(totalCost);

        BigDecimal unrealizedPLPercent = unrealizedPL.multiply(new BigDecimal(100)).divide(totalCost,RoundingMode.HALF_UP);

        PositionDto positionDto = PositionDto.builder()
                .symbol(symbol.get())
                .quantity(filledQuantity)
                .marketValue(marketValue)
                .lastPrice(lastPrice)
                .avgPrice(avgPrice)
                .totalCost(totalCost)
                .unrealizedPL(unrealizedPL)
                .unrealizedPLPercent(unrealizedPLPercent)
                .build();
        positionDto.setType();

        return positionDto;
    }

    public List<PositionDto>getOrderedPositions(String userId){
        if(!userService.isUserIdValid(userId)){
            throw new IllegalArgumentException("Unable to find user with id " + userId);
        }

        List<Symbol> orderedSymbols = orderRepository.getAllOrderedSymbolsByUserId(userId);

        List<PositionDto> orderedPositions = new ArrayList<>();
        orderedSymbols.forEach(o -> {
            if(calcQuantity(o.getSymbolId(), userId) != 0){
                PositionDto dto = getAPosition(o.getSymbolId(), userId);
                orderedPositions.add(dto);
            }
        });

        return orderedPositions;
    }
}
