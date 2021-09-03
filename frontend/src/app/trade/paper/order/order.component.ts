import { Component, OnDestroy, OnInit } from '@angular/core';
import { Order } from '../../../model/Order';
import { OrderService } from '../../../service/order.service';

import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';
import { environment } from '../../../../environments/environment';
import { StockUtils } from '../../../utils/StockUtils';
import { Subscription } from 'rxjs';
import { AuthService } from '../../../service/auth.service';


@Component({
  selector: 'app-order',
  templateUrl: './order.component.html',
  styleUrls: ['./order.component.css']
})
export class OrderComponent implements OnInit, OnDestroy {
  private utils: StockUtils;
  private stompClient;
  private serverUrl = environment.stockWS;
  private serviceSub: Subscription;
  private userId: string;
  filled = true;
  order: Order;
  orders: Order[];


  constructor(
    private orderService: OrderService,
    private authService: AuthService
  ) {
  }

  ngOnDestroy(): void {
    this.serviceSub.unsubscribe();
  }

  ngOnInit(): void {
    this.utils = new StockUtils();

    this.userId = this.authService.readToken().userId;

    this.serviceSub = this.orderService.selectedOrderEvt.subscribe(order => {
      this.orders.unshift(order);
      this.stompClient.subscribe(`/topic/trade/`, (orders) => {
        console.log(orders);
      });
    });

    this.orderService.getAllOrdersByUserId(this.userId).subscribe(orders => {
      this.orders = orders;

      if (this.stompClient != null) {
        this.stompClient.subscribe(`/user/${this.userId}/queue/order`, (orders) => {
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

    this.stompClient.connect({userId: this.userId}, (frame) => {
      copyStompClient.subscribe(`/user/${this.userId}/queue/order`, (orders) => {
       that.orders = that.utils.convertToOrders(orders.body);
       const idx = that.orders.findIndex(o => o.newlyFilled === true);
       setTimeout(() => {that.orders[idx].newlyFilled = false;}, 2000);
      });
    }, (err) => {
      console.log(err);
    });
  }


}

