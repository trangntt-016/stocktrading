import { Component, OnDestroy, OnInit } from '@angular/core';
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
export class PaperWatchlistComponent implements OnInit, OnDestroy {
  private utils = new StockUtils();
  private stompClient;
  private stompClientSub: Subscription;
  private serverUrl = environment.stockWS;
  private sub: Subscription;
  loading = true;
  watchlists: Watchlist[];
  selectedWatchlist: Watchlist;
  dailies: DailyPriceChange[];

  constructor(
    private watchlistService: WatchlistService
  ) { }

  ngOnDestroy(): void {
    this.sub.unsubscribe();
  }

  ngOnInit(): void {
    this.sub = this.watchlistService.getAllWatchlistsByUserId('U_004').subscribe(wl => {
      this.watchlists = wl;
      this.selectedWatchlist = wl[0];
      this.initializeWebSocketConnection(this.selectedWatchlist.watchlistId);
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

    this.stompClient.connect({}, (frame) => {
      that.sub = copyStompClient.subscribe(`/user/U_004/queue/watchlist/${watchlistId}`, (dailies) => {
        that.dailies = that.utils.convertToDailyPriceChange(dailies.body);
        that.loading = false;
      });
      copyStompClient.send(`/app/watchlist/${watchlistId}`, {}, (""));
    }, (err) => {
      console.log(err);
    });
  }

  updateWatchlistWS(event): any {
    const watchlistId = this.watchlists.filter(w => w.name === event.value)[0].watchlistId;

    this.sub = this.stompClient.subscribe(`/user/U_004/queue/watchlist/${watchlistId}`, (dailies) => {
      if (dailies.body == 'null'){
        this.dailies = null;
      }
      this.dailies = this.utils.convertToDailyPriceChange(dailies.body);

    });
    this.stompClient.send(`/app/watchlist/${watchlistId}`, {}, (""));
  }

}
