// https://stackblitz.com/edit/line-chart-issue?file=app%2Fdata.ts
import { Component, OnInit } from '@angular/core';
import { single } from './data';
import { DataManagerService } from '../service/data-manager.service';
import { Observable } from 'rxjs';
import { ChartDto } from '../model/HistoricalQuoteDto';
import { SummaryStock } from '../model/SummaryStock';

@Component({
  selector: 'app-test',
  templateUrl: './test.component.html',
  styleUrls: ['./test.component.css']
})
export class TestComponent implements OnInit {
  singles: ChartDto[];
  summaryStocks$: Observable<SummaryStock[]>;
  public view: any[] = [400, 200];

  public colorScheme = {
    domain: ['#5AA454']
  };
  constructor(
    private dataService: DataManagerService
  ) {

  }

  ngOnInit(): void {
    this.dataService.getHistoricalQuotesFrom().subscribe(charts =>{
      let converted = charts.map(
        chart => {
          const series = chart.series.map(s => {
            const date = new Date(s.date);
            const newDate = date.getUTCDate() + " " + date.toLocaleDateString('default',{month: 'long'});
            const seriesObj = Object.assign({
              name: newDate,
              value: s.close
            });
            return seriesObj;
          });
          const name = chart.name;
          const chartObj = Object.assign({
            name: chart.name,
            series: series
          });
          return chartObj;
        }
      )
      converted = converted.map(c=>{
        return new Array(c);
      })
      this.singles = converted;
    });

    this.dataService.findSymbol("GOOG").subscribe(t=>{
      console.log(t);
    })
    setInterval(() => {
      console.log("hi");
      this.summaryStocks$ = this.dataService.getTopStocksSummary();

    }, 5000);
  }



}
