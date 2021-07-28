import { Component, OnInit } from '@angular/core';
import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';
import { environment } from '../../../../environments/environment';

import { WatchlistService } from '../../../service/watchlist.service';
import { Watchlist } from '../../../model/Watchlist';
import { DailyDetails } from '../../../model/DailyDetails';
import { StockUtils } from '../../../utils/StockUtils';



@Component({
  selector: 'app-key-stats',
  templateUrl: './key-stats.component.html',
  styleUrls: ['./key-stats.component.css']
})
export class KeyStatsComponent implements OnInit {
  daily: DailyDetails;
  selectedSymbolId: number;
  selectedWatchlist: Watchlist;
  private stompClient;
  private stompClientSub;
  private serverUrl = environment.stockWS;
  private utils: StockUtils;

  constructor(
    private watchlistService: WatchlistService
  ) { }


  ngOnInit(): void {
    this.utils = new StockUtils();

    this.daily = new DailyDetails();

    // subscribe to any changes/initialization in the selected watchlist
    this.watchlistService.selectedWatchlistEvt.subscribe(selected => {
      if (selected.symbols.length > 0){
        this.selectedSymbolId = selected.symbols[0].symbolId;
        this.initializeWebSocketConnection(this.selectedSymbolId);
      }
    });

    // listen to any changes when there's new daily selected
    this.watchlistService.selectedDailyEvt.subscribe(daily => {
      this.selectedSymbolId = daily.symbol.symbolId;
      // subscribe to the selected symbol
      this.stompClient.subscribe(`/topic/${this.selectedSymbolId}`, (daily) => {
        this.daily = this.utils.convertToDaily(daily.body);
      });
      // get updated from new selected symbol id;
      this.stompClient.send(`/app/${this.selectedSymbolId}`, {}, (""));
    });

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
        copyStompClient.subscribe(`/topic/${symbolId}`, (daily) => {
          that.daily = that.utils.convertToDaily(daily.body);
        });
      copyStompClient.send(`/app/${symbolId}`, {}, (""));
    }, (err) => {
      console.log(err);
    });
  }

}
