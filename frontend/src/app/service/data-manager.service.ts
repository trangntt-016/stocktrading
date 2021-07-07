import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ChartDto } from '../model/HistoricalQuoteDto';
import { SummaryStock } from '../model/SummaryStock';
import { SymbolDto } from '../model/SymbolDto';

@Injectable({
  providedIn: 'root'
})
export class DataManagerService {
  constructor(
    private http: HttpClient
  ) { }

  getHistoricalQuotesFrom(): Observable<ChartDto[]>{
    return this.http.get<ChartDto[]>(`http://localhost:3000/api/quotes/charts-mainpage`);
  }

  getTopStocksSummary(): Observable<SummaryStock[]>{
    return this.http.get<SummaryStock[]>(`http://localhost:3000/api/quotes/topthree`);
  }

  getAllSymbols(): Observable<SymbolDto[]>{
    return this.http.get<SymbolDto[]>(`http://localhost:3000/api/symbols`);
  }

}
