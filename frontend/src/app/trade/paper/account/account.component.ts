import { Component, OnInit, Output, EventEmitter} from '@angular/core';
import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';
import { environment } from '../../../../environments/environment';
import { StockUtils } from '../../../utils/StockUtils';
import { Account } from '../../../model/Account';

@Component({
  selector: 'app-account',
  templateUrl: './account.component.html',
  styleUrls: ['./account.component.css']
})
export class AccountComponent implements OnInit {
  @Output() buyingPower = new EventEmitter();
  private serverUrl = environment.stockWS;
  private stompClient;
  private stompClientSub;
  private utils: StockUtils;
  account: Account = new Account();

  constructor() { }

  ngOnInit(): void {
    this.utils = new StockUtils();

    this.initializeWebSocketConnection("U_004");
  }

  initializeWebSocketConnection(userId: string): any {
    console.log('connected to ws ...');

    const ws = new SockJS(this.serverUrl);

    this.stompClient = Stomp.over(ws);

    this.stompClientSub = Stomp.over(ws);

    const copyStompClient = this.stompClient;

    const that = this;

    this.stompClient.connect({}, function(frame) {
      copyStompClient.subscribe(`/user/U_004/queue/account`, (account) => {
        that.account = that.utils.convertToAccount(account.body);
        that.buyingPower.emit(that.account.buyingPower);
      });
      copyStompClient.send(`/app/account`, {}, ("U_004"));
    }, (err) => {
      console.log(err);
    });
  }

}
