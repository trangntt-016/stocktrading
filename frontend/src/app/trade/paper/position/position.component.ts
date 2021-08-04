import { Component, OnDestroy, OnInit } from '@angular/core';
import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';
import { StockUtils } from '../../../utils/StockUtils';
import { Subscription } from 'rxjs';
import { environment } from '../../../../environments/environment';
import { Position } from '../../../model/Position';
import { AuthService } from '../../../service/auth.service';


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
  private userId: string;

  constructor(
    private authService: AuthService
  ) {
  }

  ngOnInit(): void {
    this.userId = this.authService.readToken().userId;

    this.initializeWebSocketConnection(this.userId);
  }

  initializeWebSocketConnection(userId: string): any {
    console.log('connected to ws ...');

    const ws = new SockJS(this.serverUrl);
    this.stompClient = Stomp.over(ws);
    this.stompClientSub = Stomp.over(ws);

    const copyStompClient = this.stompClient;
    // it helps to render data on html
    const that = this;

    this.stompClient.connect({userId: this.userId}, (frame) => {
      copyStompClient.subscribe(`/user/${this.userId}/queue/position`, (positions) => {
        that.positions = that.utils.convertToPositions(positions.body);
      });
      copyStompClient.send(`/app/position`, {}, (this.userId));
    }, (err) => {
      console.log(err);
    });
  }

}
