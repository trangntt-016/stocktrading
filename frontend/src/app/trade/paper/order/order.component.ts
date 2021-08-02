import { Component, OnDestroy, OnInit } from '@angular/core';
import { Order } from '../../../model/Order';
import { OrderService } from '../../../service/order.service';

import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';
import { environment } from '../../../../environments/environment';
import { StockUtils } from '../../../utils/StockUtils';
import { Subscription } from 'rxjs';


@Component({
  selector: 'app-order',
  templateUrl: './order.component.html',
  styleUrls: ['./order.component.css']
})
export class OrderComponent implements OnInit, OnDestroy {
  order: Order;
  orders: Order[];
  private utils: StockUtils;
  private stompClient;
  private serverUrl = environment.stockWS;
  private serviceSub: Subscription;

  constructor(
    private orderService: OrderService
  ) {
  }

  ngOnDestroy(): void {
    this.serviceSub.unsubscribe();
  }

  ngOnInit(): void {
    this.utils = new StockUtils();

    this.serviceSub = this.orderService.selectedOrderEvt.subscribe(order => {
      this.orders.unshift(order);
      this.stompClient.subscribe(`/topic/trade/`, (orders) => {
      });
    });

    this.orderService.getAllOrdersByUserId('U_004').subscribe(orders => {
      this.orders = orders;
      if (this.stompClient != null) {
        this.stompClient.subscribe(`/user/U_004/queue/order`, (orders) => {
          this.orders = this.utils.convertToOrders(orders.body);
        });
      }

    });

    this.initializeWebSocketConnection();
  }

  initializeWebSocketConnection(): void {

    console.log('connected to chat ...');

    const ws = new SockJS(this.serverUrl);
    this.stompClient = Stomp.over(ws);
    const copyStompClient = this.stompClient;

    const that = this;

    this.stompClient.connect({userId: 'U_004'}, (frame) => {
      copyStompClient.subscribe(`/user/U_004/queue/order`, (orders) => {
       that.utils.convertToOrders(orders.body);
      });

    }, (err) => {
      console.log(err);
    });
  }


}

