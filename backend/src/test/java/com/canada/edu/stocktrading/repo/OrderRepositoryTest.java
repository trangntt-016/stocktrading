package com.canada.edu.stocktrading.repo;

import com.canada.edu.stocktrading.model.*;
import com.canada.edu.stocktrading.repository.OrderRepository;
import com.canada.edu.stocktrading.repository.SymbolRepository;
import com.canada.edu.stocktrading.service.utils.ConvertTimeUtils;
import com.canada.edu.stocktrading.utils.EntityUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

@SpringBootTest
public class OrderRepositoryTest {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private SymbolRepository symbolRepository;

    @Autowired
    EntityUtils entityUtils;

    @Test
    public void testSaveOrder(){
        Symbol randomSymbol = entityUtils.generateRandomEntity(symbolRepository,symbolRepository.findAll().get(0).getSymbolId());

        Order order = Order.builder()
                .limitPrice(new BigDecimal(55.5))
                .symbol(randomSymbol)
                .orderSide(OrderSide.BUY)
                .orderType(OrderType.LIMIT)
                .filledQuantity(5)
                .orderPlaced(new Date())
                .orderStatus(OrderStatus.WORKING)
                .build();

        int before = orderRepository.findAll().size();
        orderRepository.save(order);
        int after = orderRepository.findAll().size();

        assertThat(after).isEqualTo(before+1);
    }

    @Test
    public void testUpdateOrderStatus(){
        Order randomOrder = entityUtils.generateRandomEntity(orderRepository,orderRepository.findAll().get(0).getOrderId());
        orderRepository.updateOrderStatus(randomOrder.getOrderId(),OrderStatus.FILLED);
        assertThat(orderRepository.findById(randomOrder.getOrderId()).get().getOrderStatus()).isEqualTo(OrderStatus.FILLED);
    }

    @Test
    public void testUpdateOrderFilledTime(){
        Timestamp ts = ConvertTimeUtils.convertCurrentTimeTo14July();
        Order randomOrder = entityUtils.generateRandomEntity(orderRepository,orderRepository.findAll().get(0).getOrderId());
        orderRepository.updateFilledTime(randomOrder.getOrderId(),ts);
        assertThat(orderRepository.findById(randomOrder.getOrderId()).get().getFilledTime()).isEqualTo(ts);
    }
}
