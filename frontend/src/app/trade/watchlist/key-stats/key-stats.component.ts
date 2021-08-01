import { Component, OnDestroy, OnInit } from '@angular/core';
import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';
import { environment } from '../../../../environments/environment';

import { WatchlistService } from '../../../service/watchlist.service';
import { DailyDetails } from '../../../model/DailyDetails';
import { StockUtils } from '../../../utils/StockUtils';
import { Subscription } from 'rxjs';


@Component({
  selector: 'app-key-stats',
  templateUrl: './key-stats.component.html',
  styleUrls: ['./key-stats.component.css']
})
export class KeyStatsComponent implements OnInit, OnDestroy {
  daily: DailyDetails;
  selectedSymbolId: number;
  private stompClient;
  private stompClientSub;
  private serverUrl = environment.stockWS;
  private utils: StockUtils;
  private subscription: Subscription;
  private wsSubscription: Subscription;

  constructor(
    private watchlistService: WatchlistService
  ) {
  }


  ngOnInit(): void {
    this.utils = new StockUtils();

    this.daily = new DailyDetails();

    // subscribe to any changes/initialization in the selected watchlist
    this.subscription = this.watchlistService.selectedWatchlistEvt.subscribe(selected => {
      if (selected.symbols.length > 0) {
        this.selectedSymbolId = selected.symbols[0].symbolId;
        this.initializeWebSocketConnection(this.selectedSymbolId);
      }
    });

    // listen to any changes when there's new daily selected
    this.subscription = this.watchlistService.selectedDailyEvt.subscribe(daily => {
      this.selectedSymbolId = daily.symbol.symbolId;
      // subscribe to the selected symbol
      this.wsSubscription = this.stompClient.subscribe(`/topic/${this.selectedSymbolId}`, (daily) => {
        this.daily = this.utils.convertToDaily(daily.body);
      });
      // get updated from new selected symbol id;
      this.stompClient.send(`/app/${this.selectedSymbolId}`, {}, (''));
    });
  }

  ngOnDestroy(): void {
    this.wsSubscription.unsubscribe();
    this.subscription.unsubscribe();
  }


  initializeWebSocketConnection(symbolId: number): any {
    console.log('connected to ws ...');

    const ws = new SockJS(this.serverUrl);
    this.stompClient = Stomp.over(ws);
    this.stompClientSub = Stomp.over(ws);

    const copyStompClient = this.stompClient;
    // it helps to render data on html
    const that = this;

    this.stompClient.connect({}, function(frame) {
      that.wsSubscription = copyStompClient.subscribe(`/topic/${symbolId}`, (daily) => {
        that.daily = that.utils.convertToDaily(daily.body);
      });
      copyStompClient.send(`/app/${symbolId}`, {}, (''));
    }, (err) => {
      console.log(err);
    });
  }

}
