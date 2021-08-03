import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';

import { ApexAxisChartSeries, ApexChart, ApexTitleSubtitle, ApexXAxis, ApexYAxis } from 'ng-apexcharts';
import { YahooFinanceService } from '../../../service/yahoo-finance.service';
import { Symbol } from '../../../model/Symbol';
import { Subscription } from 'rxjs';
import { SymbolService } from '../../../service/symbol.service';

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
  symbols: Symbol[];
  autoSymbols: Symbol[];
  interval: number;
  searchSymbol: string;
  chartOptions: Partial<ChartOptions>;
  @ViewChild('chart') chart: ChartComponent;

  constructor(
    private yahooService: YahooFinanceService,
    private symbolService: SymbolService
  ) {
  }


  ngOnInit(): void {
    this.interval = 90;

    this.symbolService.selectedSymbolEvt.subscribe(symbol => {
      this.searchSymbol = symbol.symbol;
      this.yahooService.getHistoricalQuotes(symbol.symbol, this.interval).subscribe(res => {
        this.chartOptions = res;
      });
    });

    this.symbolService.getAllSymbols().subscribe(symbols => {
      this.symbols = symbols;
    });
  }

  doFilter(): void {
    this.autoSymbols = this.filter(this.symbols);
    const symbol = this.symbols.filter(s => s.symbol.toUpperCase().includes(this.searchSymbol))[0];
  }

  filter(values): any {
    return values.filter(symbol => {
      return symbol.symbol.toUpperCase().includes(this.searchSymbol.toUpperCase());
    });
  }

  setInterval(interval: number): void {
    this.interval = interval;
    this.yahooService.getHistoricalQuotes('AMZN', this.interval).subscribe(res => {
      this.chartOptions = res;
    });
  }

  selectSymbol(symbol): void{
    if (symbol !== undefined) {
      this.symbolService.sendSelectedSymbol(symbol);
    }
  }
}
