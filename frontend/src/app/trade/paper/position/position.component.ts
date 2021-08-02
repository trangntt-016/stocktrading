import { Component, OnDestroy, OnInit } from '@angular/core';
import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';
import { StockUtils } from '../../../utils/StockUtils';
import { Subscription } from 'rxjs';
import { environment } from '../../../../environments/environment';
import { Position } from '../../../model/Position';


@Component({
  selector: 'app-position',
  templateUrl: './position.component.html',
  styleUrls: ['./position.component.css']
})
export class PositionComponent implements OnInit {
  positions: Position[];
  private utils = new StockUtils();
  private stompClient;
  private stompClientSub: Subscription;
  private serverUrl = environment.stockWS;

  constructor() {
  }

  ngOnInit(): void {
    this.initializeWebSocketConnection('U_004');
  }

  initializeWebSocketConnection(userId: string): any {
    console.log('connected to ws ...');

    const ws = new SockJS(this.serverUrl);
    this.stompClient = Stomp.over(ws);
    this.stompClientSub = Stomp.over(ws);

    const copyStompClient = this.stompClient;
    // it helps to render data on html
    const that = this;

    this.stompClient.connect({userId: 'U_004'}, (frame) => {
      copyStompClient.subscribe(`/user/U_004/queue/position`, (positions) => {
        that.positions = that.utils.convertToPositions(positions.body);
      });
      copyStompClient.send(`/app/position`, {}, ('U_004'));
    }, (err) => {
      console.log(err);
    });
  }

}
