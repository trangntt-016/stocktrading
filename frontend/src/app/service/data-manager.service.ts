import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ChartDto } from '../model/HistoricalQuoteDto';
import { SummaryStock } from '../model/SummaryStock';

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

  findSymbol(symbol: string): Observable<any>{
    return this.http.get<any>(`https://query1.finance.yahoo.com/v7/finance/quote?symbols=GOOG`);
  }
}
