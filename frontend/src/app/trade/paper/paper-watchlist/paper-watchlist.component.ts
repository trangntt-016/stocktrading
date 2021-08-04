import { Component, OnDestroy, OnInit } from '@angular/core';
import { WatchlistService } from '../../../service/watchlist.service';
import { Watchlist } from '../../../model/Watchlist';
import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';
import { environment } from '../../../../environments/environment';
import { Subscription } from 'rxjs';
import { StockUtils } from '../../../utils/StockUtils';
import { DailyPriceChange } from '../../../model/DailyPriceChange';
import { AuthService } from '../../../service/auth.service';


@Component({
  selector: 'app-paper-watchlist',
  templateUrl: './paper-watchlist.component.html',
  styleUrls: ['./paper-watchlist.component.css']
})
export class PaperWatchlistComponent implements OnInit {
  private utils = new StockUtils();
  private stompClient;
  private stompClientSub: Subscription;
  private serverUrl = environment.stockWS;
  private userId: string;
  loading = true;
  watchlists: Watchlist[];
  selectedWatchlist: Watchlist;
  dailies: DailyPriceChange[];

  constructor(
    private watchlistService: WatchlistService,
    private authService: AuthService
  ) { }


  ngOnInit(): void {
    this.userId = this.authService.readToken().userId;

    this.watchlistService.getAllWatchlistsByUserId(this.userId).subscribe(wl => {
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
      copyStompClient.subscribe(`/user/${this.userId}/queue/watchlist/${watchlistId}`, (dailies) => {
        that.dailies = that.utils.convertToDailyPriceChange(dailies.body);
        that.loading = false;
      });
      copyStompClient.send(`/app/watchlist`, {}, ('userId:' + this.userId + ',watchlistId:' + watchlistId));
    }, (err) => {
      console.log(err);
    });
  }

  updateWatchlistWS(event): any {
    const watchlistId = this.watchlists.filter(w => w.name === event.value)[0].watchlistId;

    this.stompClient.subscribe(`/user/${this.userId}/queue/watchlist/${watchlistId}`, (dailies) => {
      if (dailies.body === 'null'){
        this.dailies = null;
      }
      this.dailies = this.utils.convertToDailyPriceChange(dailies.body);

    });
    this.stompClient.send(`/app/watchlist`, {}, ('userId:' + this.userId + ',watchlistId:' + watchlistId));
  }

}
