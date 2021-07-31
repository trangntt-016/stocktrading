import { Component, OnInit } from '@angular/core';
import { WatchlistService } from '../../../service/watchlist.service';
import { Watchlist } from '../../../model/Watchlist';
import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';
import { environment } from '../../../../environments/environment';
import { Subscription } from 'rxjs';
import { StockUtils } from '../../../utils/StockUtils';
import { DailyPriceChange } from '../../../model/DailyPriceChange';


@Component({
  selector: 'app-paper-watchlist',
  templateUrl: './paper-watchlist.component.html',
  styleUrls: ['./paper-watchlist.component.css']
})
export class PaperWatchlistComponent implements OnInit {
  watchlists: Watchlist[];
  selectedWatchlist: Watchlist;
  dailies: DailyPriceChange[];
  private utils = new StockUtils();
  private stompClient;
  private stompClientSub: Subscription;
  private serverUrl = environment.stockWS;

  constructor(
    private watchlistService: WatchlistService
  ) { }

  ngOnInit(): void {
    this.watchlistService.getAllWatchlistsByUserId('U_004').subscribe(wl => {
      this.watchlists = wl;
      this.selectedWatchlist = wl[0];
      //this.initializeWebSocketConnection(this.selectedWatchlist.watchlistId);
    });
  }

  initializeWebSocketConnection(watchlistId: number): any {
    console.log('connected to ws ...');

    const ws = new SockJS(this.serverUrl);
    this.stompClient = Stomp.over(ws);
    this.stompClientSub = Stomp.over(ws);

    const copyStompClient = this.stompClient;
    // it helps to render data on html
    const that = this;

    this.stompClient.connect({}, function(frame) {
      copyStompClient.subscribe(`/user/U_004/queue/watchlist/${watchlistId}`, (dailies) => {
        that.dailies = that.utils.convertToDailyPriceChange(dailies.body);
      });
      copyStompClient.send(`/app/watchlist/${watchlistId}`, {}, (""));
    }, (err) => {
      console.log(err);
    });
  }

  updateWatchlistWS(event):any {
    const watchlistId = this.watchlists.filter(w => w.name === event.value)[0].watchlistId;
    this.stompClient.subscribe(`/user/U_004/queue/watchlist/${watchlistId}`, (dailies) => {
      this.dailies = this.utils.convertToDailyPriceChange(dailies.body);
      console.log(this.dailies);
    });
    this.stompClient.send(`/app/watchlist/${watchlistId}`, {}, (""));
  }

}
