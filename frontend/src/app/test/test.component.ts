// https://stackblitz.com/edit/line-chart-issue?file=app%2Fdata.ts
import { Component, OnDestroy, OnInit } from '@angular/core';
import { single } from './data';
import { DataManagerService } from '../service/data-manager.service';
import { Observable, Subscription } from 'rxjs';
import { ChartDto } from '../model/HistoricalQuoteDto';
import { SummaryStock } from '../model/SummaryStock';

@Component({
  selector: 'app-test',
  templateUrl: './test.component.html',
  styleUrls: ['./test.component.css']
})
export class TestComponent implements OnInit, OnDestroy {
  singles: any;
  summaryStocks: SummaryStock[];
  summaryStocksSub: Subscription;

  public view: any[] = [400, 150];

  public colorScheme = {
    domain: ['#5AA454']
  };
  constructor(
    private dataService: DataManagerService
  ) {

  }

  ngOnInit(): void {
    this.getSummaryStocks();
    setInterval(() => {this.getSummaryStocks()}, 5000);

     this.dataService.getHistoricalQuotesFrom().subscribe(charts =>{
      this.singles = charts;
     });

  }

  ngOnDestroy() {
    this.summaryStocksSub.unsubscribe();
  }

  public getSummaryStocks(){
    this.summaryStocksSub = this.dataService.getTopStocksSummary().subscribe(s =>{
      this.summaryStocks = s;
    });
  }



}
