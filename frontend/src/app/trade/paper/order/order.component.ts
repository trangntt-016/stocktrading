import { Component, OnInit } from '@angular/core';
import { Order } from '../../../model/Order';
import { OrderService } from '../../../service/order.service';

import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';
import { environment } from '../../../../environments/environment';


@Component({
  selector: 'app-order',
  templateUrl: './order.component.html',
  styleUrls: ['./order.component.css']
})
export class OrderComponent implements OnInit {
  order: Order;
  orders: Order[];
  private stompClient;
  private serverUrl = environment.stockWS;

  constructor(
    private orderService: OrderService
  ) {
  }

  ngOnInit(): void {
    this.orderService.selectedOrderEvt.subscribe(order => {
      this.orders.unshift(order);
      this.stompClient.subscribe(`/topic/trade/`, (orders) => {
        console.log(orders);
      });
    });

    this.orderService.getAllOrdersByUserId("U_004").subscribe(orders => {
      this.orders = orders;
      this.stompClient.subscribe(`/user/U_004/queue/order`, (orders) => {
        console.log(orders);
      });
    });

    this.initializeWebSocketConnection();
  }

  initializeWebSocketConnection(): void {

    console.log('connected to chat ...');

    const ws = new SockJS(this.serverUrl);
    this.stompClient = Stomp.over(ws);
    const copyStompClient = this.stompClient;

    const that = this;

    this.stompClient.connect({userId:"U_004"}, function(frame) {
      copyStompClient.subscribe(`/user/U_004/queue/order`, (daily) => {
        console.log(daily);
      });

    }, (err) => {
      console.log(err);
    });
  }


}

