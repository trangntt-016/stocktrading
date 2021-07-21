import { AfterContentChecked, AfterContentInit, AfterViewChecked, AfterViewInit, Component, OnInit } from '@angular/core';
import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';

import { WatchlistService } from '../../../service/watchlist.service';
import { Watchlist } from '../../../model/Watchlist';
import { DailyDetails } from '../../../model/DailyDetails';



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
  private serverUrl = "http://localhost:3000/stocks";

  constructor(
    private watchlistService: WatchlistService
  ) { }


  ngOnInit(): void {
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
        this.daily = convertToDaily(daily.body);
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
          that.daily = convertToDaily(daily.body);
        });
      copyStompClient.send(`/app/${symbolId}`, {}, (""));
    }, (err) => {
      console.log(err);
    });
  }


}

function convertToDaily(newMes): DailyDetails {
  let daily = new DailyDetails();
  newMes = newMes.replace("[","").replace("]","");
  let processed = newMes.split(",");
  processed.forEach(val => {
    const array = val.split(":");
    const key = array[0].replace(",","");
    const value = array[1].replace(",","");
    switch(key){
      case 'dailyId':
        daily.dailyId = +value;
        break;
      case 'timestamp':
        daily.timestamp = value;
        break;
      case 'price':
        daily.price = +value;
        break;
      case 'open':
        daily.open = +value;
        break;
      case 'high':
        daily.high = +value;
        break;
      case 'low':
        daily.low = +value;
        break;
      case 'close':
        daily.close = +value;
        break;
      case 'volume':
        daily.volume = +value;
        break;
      case 'avg_vol_3_months':
        daily.avg_vol_3_months = +value;
        break;
      case 'prev_close':
        daily.prevClose = +value;
        break;
      case 'week_high_52':
        daily.week_high_52 = +value;
        break;
      case 'week_low_52':
        daily.week_low_52 = +value;
        break;
      case 'symbol':
        daily.symbol = value;
        break;
      case 'change':
        daily.change = value;
        break;
      case 'changeInPercent':
        daily.changeInPercent = value;
        break;
      default:
        null;
    }
  });
  return daily;
}
