import { Component, OnInit } from '@angular/core';
import { DataManagerService } from '../../service/data-manager.service';
import { interval, Observable, timer } from 'rxjs';
import {single, trendingTicker} from '../../test/data';
import { TickerService } from './ticker.service';

@Component({
  selector: 'app-trending-tickers',
  templateUrl: './trending-tickers.component.html',
  styleUrls: ['./trending-tickers.component.css']
})
export class TrendingTickersComponent implements OnInit {
  trendingTickers$: Observable<any>;
  tickerDetails: any[] = new Array();
  test: any = single;
  trendingTicker: any = trendingTicker;

  view: any[] = [370, 150];

  public colorScheme = {
    domain: ['#5AA454']
  };

  constructor(
    private tickerService: TickerService
  ) { }

  ngOnInit(): void {
  this.trendingTickers$ = this.tickerService.getTrendingTickers();
  }

}
