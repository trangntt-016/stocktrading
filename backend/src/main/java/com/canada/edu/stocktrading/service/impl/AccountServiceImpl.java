package com.canada.edu.stocktrading.service.impl;

import com.canada.edu.stocktrading.dto.PositionDto;
import com.canada.edu.stocktrading.model.Account;
import com.canada.edu.stocktrading.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private PositionServiceImpl positionService;

    @Autowired
    private UserEntityServiceImpl userService;

    @Override
    public Account getAccountByUserId(String userId) {
        if(!userService.isUserIdValid(userId)) {
            throw new IllegalArgumentException("Unable to find user with id " + userId);
        }
        List<PositionDto> dtos = positionService.getOrderedPositions(userId);

        Double marketValDouble = dtos.stream().map(PositionDto::getMarketValue).mapToDouble(BigDecimal::doubleValue).sum();

        BigDecimal marketValue = new BigDecimal(marketValDouble);

        Double totalCostDouble = dtos.stream().map(PositionDto::getTotalCost).mapToDouble(BigDecimal::doubleValue).sum();

        BigDecimal totalCost = new BigDecimal(totalCostDouble);

        BigDecimal initialVal = new BigDecimal(10000);

        BigDecimal buyingPower = initialVal.subtract(totalCost);

        BigDecimal overallPL = marketValue.subtract(totalCost);

        BigDecimal overallPLPercent = marketValue.multiply(new BigDecimal(100)).divide(initialVal, RoundingMode.HALF_UP);

        BigDecimal netAccountValue = initialVal.add(overallPL);

        Account account = Account.builder()
                .userId(userId)
                .netAccountValue(netAccountValue)
                .overallPL(overallPL)
                .initialValue(initialVal)
                .PLChange(overallPLPercent)
                .marketValue(marketValue)
                .buyingPower(buyingPower)
                .build();

        return account;
    }
}
