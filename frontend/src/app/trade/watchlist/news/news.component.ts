import { Component, OnInit } from '@angular/core';
import { WatchlistService } from '../../../service/watchlist.service';
import { Watchlist } from '../../../model/Watchlist';
import {Symbol} from '../../../model/Symbol';

@Component({
  selector: 'app-news',
  templateUrl: './news.component.html',
  styleUrls: ['./news.component.css']
})
export class NewsComponent implements OnInit {
  news: any[] = new Array();
  symbols: Symbol[];
  constructor(
    private watchlistService: WatchlistService
  ) { }

  ngOnInit(): void {
    this.watchlistService.watchlistEvt.subscribe(watchlists => {
      this.symbols = new Array();
      this.news = new Array();
      watchlists.forEach(wl => {
        wl.symbols.forEach(symbol => {
          this.symbols.push(symbol);
        })
      })

      this.symbols.forEach(symbol => {
        this.watchlistService.getNewsBySymbol(symbol).subscribe(news => {
          for(let i = 0; i < 10; i++){
            news[i].symbol = symbol.symbol;
            this.news.push(news[i]);
          }
        })
      })
    })
  }

}
