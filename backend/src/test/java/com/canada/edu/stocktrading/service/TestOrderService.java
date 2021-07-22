package com.canada.edu.stocktrading.service;

import com.canada.edu.stocktrading.model.Order;
import com.canada.edu.stocktrading.model.OrderSide;
import com.canada.edu.stocktrading.model.OrderType;
import com.canada.edu.stocktrading.model.Symbol;
import com.canada.edu.stocktrading.repository.OrderRepository;
import com.canada.edu.stocktrading.repository.SymbolRepository;
import com.canada.edu.stocktrading.service.dto.OrderDto;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TestOrderService {

    @InjectMocks
    private OrderService orderService;

    @Test
    public void testCreateNewOrder(){
        Symbol symbol = Symbol.builder().symbolId(1).symbol("PYPL").name("PayPal Holdings, Inc. Common Stock").build();
        OrderDto dto = OrderDto.builder()
                .limitPrice(new BigDecimal(55.5))
                .symbol(symbol)
                .orderSide("BUY")
                .orderType("LIMIT")
                .build();

        Order order = Order.builder()
                .limitPrice(new BigDecimal(55.5))
                .symbol(symbol)
                .orderSide(OrderSide.BUY)
                .orderType(OrderType.LIMIT)
                .filledQuantity(dto.getFilledQuantity())
                .build();
        when(orderService.save(dto)).thenReturn(order);

    }

}
