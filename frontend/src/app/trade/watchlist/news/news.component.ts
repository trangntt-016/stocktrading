import { Component, OnDestroy, OnInit } from '@angular/core';
import { WatchlistService } from '../../../service/watchlist.service';
import { Symbol } from '../../../model/Symbol';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-news',
  templateUrl: './news.component.html',
  styleUrls: ['./news.component.css']
})
export class NewsComponent implements OnInit, OnDestroy {
  news: any[] = [];
  loading = true;
  symbols: Symbol[];
  private subscription: Subscription;

  constructor(
    private watchlistService: WatchlistService
  ) {
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  ngOnInit(): void {
    this.subscription = this.watchlistService.watchlistEvt.subscribe(watchlists => {
      this.symbols = [];
      this.news = [];
      watchlists.forEach(wl => {
        wl.symbols.forEach(symbol => {
          this.symbols.push(symbol);
        });
      });

      this.symbols.forEach(symbol => {
        this.subscription = this.watchlistService.getNewsBySymbol(symbol).subscribe(news => {
          this.loading = false;
          for (let i = 0; i < 10; i++) {
            news[i].symbol = symbol.symbol;
            this.news.push(news[i]);
          }
        });
      });
    });
  }

}
