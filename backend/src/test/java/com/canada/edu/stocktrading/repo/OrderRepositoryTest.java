package com.canada.edu.stocktrading.repo;

import com.canada.edu.stocktrading.model.*;
import com.canada.edu.stocktrading.repository.OrderRepository;
import com.canada.edu.stocktrading.repository.SymbolRepository;
import com.canada.edu.stocktrading.repository.UserEntityRepository;
import com.canada.edu.stocktrading.utils.ConvertTimeUtils;
import com.canada.edu.stocktrading.utils.EntityUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
public class OrderRepositoryTest {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private SymbolRepository symbolRepository;

    @Autowired
    private UserEntityRepository userEntityRepository;

    @Autowired
    EntityUtils entityUtils;

    @Test
    public void testSaveOrder(){
        Symbol randomSymbol = entityUtils.generateRandomEntity(symbolRepository,symbolRepository.findAll().get(0).getSymbolId());

        UserEntity randomUser = entityUtils.generateRandomUser();

        Order order = Order.builder()
                .limitPrice(new BigDecimal(55.5))
                .symbol(randomSymbol)
                .orderSide(OrderSide.BUY)
                .orderType(OrderType.LIMIT)
                .filledQuantity(5)
                .orderPlaced(new Date())
                .orderStatus(OrderStatus.WORKING)
                .user(randomUser)
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

    @Test
    public void testGetAllOrdersByUserId(){
        UserEntity randomUsr = this.entityUtils.generateRandomUser();

        List<Order> orders = orderRepository.getAllOrdersByUserId(randomUsr.getUserId());

        if(orders.size() > 0){
            assertThat(orders.stream().filter(order -> order.getUser().getUserId().equals(randomUsr.getUserId())).collect(Collectors.toList()).size()).isGreaterThan(0);
        }
    }

    @Test
    public void testGetAllByStatus(){
        List<Order>orders = orderRepository.findAllByStatus(OrderStatus.WORKING);

        if(orders.size()>0){
            assertThat(orders.stream().filter(o-> o.getOrderStatus().name().equals("WORKING")).collect(Collectors.toList()).size()).isGreaterThan(0);
        }
    }

    @Test
    public void testUpdateAveragePrice(){
        List<Order>orders = orderRepository.findAllByStatus(OrderStatus.FILLED);

        orderRepository.updateAveragePrice(orders.get(0).getOrderId(), orders.get(0).getLimitPrice());
    }

    @Test
    public void testCalculateNumberOfBuyOrders(){
        Order randomOrder = entityUtils.generateRandomEntity(orderRepository,orderRepository.findAll().get(0).getOrderId());

        Integer symbolId = randomOrder.getSymbol().getSymbolId();

        String userId = randomOrder.getUser().getUserId();

        Integer noOfBuyOrders = orderRepository.calcNumberOfOrders(OrderStatus.FILLED,OrderSide.BUY,symbolId,userId);

        Integer noOfSellOrders = orderRepository.calcNumberOfOrders(OrderStatus.FILLED,OrderSide.SELL,symbolId,userId);

        Integer noOfOrders = noOfBuyOrders - noOfSellOrders;
    }

    @Test
    public void testCalcTotalAmountOfOrdersBySymbolUserSide() {
        Order randomOrder = entityUtils.generateRandomEntity(orderRepository,orderRepository.findAll().get(0).getOrderId());

        BigDecimal total = orderRepository.calcTotalAmountOfOrdersBySymbolUserSide("BUY",randomOrder.getSymbol().getSymbolId(),randomOrder.getUser().getUserId());

        assertThat(total).isGreaterThan(new BigDecimal(0));
    }

    @Test
    public void testGetAllOrderedSymbolsByUserId(){
        UserEntity randomUser = entityUtils.generateRandomUser();

        List<Symbol> symbols = orderRepository.getAllOrderedSymbolsByUserId(randomUser.getUserId());

        assertThat(symbols.size()).isGreaterThanOrEqualTo(0);
    }

    @Test
    public void testDeleteAllOrders() {
        orderRepository.deleteAll();

        assertThat(orderRepository.findAll().isEmpty());
    }
}
