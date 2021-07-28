import { Component, VERSION , ViewChild, OnInit } from '@angular/core';

import {ApexAxisChartSeries, ApexChart, ApexYAxis, ApexXAxis, ApexTitleSubtitle} from 'ng-apexcharts';
import { YahooFinanceService } from '../../../service/yahoo-finance.service';
import { Symbol } from '../../../model/Symbol';
import { Observable } from 'rxjs';
import { SymbolService } from '../../../service/symbol.service';
import { map } from 'rxjs/operators';

export type ChartOptions = {
  series: ApexAxisChartSeries;
  chart: ApexChart;
  xaxis: ApexXAxis;
  yaxis: ApexYAxis;
  title: ApexTitleSubtitle;
};

@Component({
  selector: 'app-chart',
  templateUrl: './chart.component.html',
  styleUrls: ['./chart.component.css']
})
export class ChartComponent implements OnInit {
  symbols$: Observable<Symbol[]>;

  interval: number;

  searchSymbol: string;

  public chartOptions: Partial<ChartOptions>;

  @ViewChild("chart") chart: ChartComponent;

  constructor(
    private yahooService: YahooFinanceService,
    private symbolService: SymbolService
  ) {
  }

  ngOnInit(){
    this.interval = 30;
    this.yahooService.getHistoricalQuotes("AMZN",this.interval).subscribe(res=>{
      this.chartOptions = res;
    })

    this.symbols$ = this.symbolService.getAllSymbols();
  }

  doFilter(): void{
    this.symbols$ = this.symbols$
      .pipe(map(symbols => this.filter(symbols)),
      );
  }

  filter(values): any{
    return values.filter(symbol => {
      return symbol.symbol.toUpperCase().includes(this.searchSymbol.toUpperCase());
    });
  }

  setInterval(interval: number): void{
    this.interval = interval;
    this.yahooService.getHistoricalQuotes("AMZN",this.interval).subscribe(res=>{
      this.chartOptions = res;
    })
  }
}
