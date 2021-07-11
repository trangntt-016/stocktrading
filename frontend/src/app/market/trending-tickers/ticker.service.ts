import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { forkJoin, Observable } from 'rxjs';
import { map, mergeMap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class TickerService {
  // javatechie javatechie.016@gmail.com abcABC123456  46e933289emshcefa6f248988445p172b80jsn0a322687ee93
  YAHOO_FINANCE_URL = "https://apidojo-yahoo-finance-v1.p.rapidapi.com/market";
  ALPHA_VANTAGE_URL = "https://alpha-vantage.p.rapidapi.com/query?function=TIME_SERIES_DAILY";
  header = new HttpHeaders({
    'X-RapidAPI-Key': '46e933289emshcefa6f248988445p172b80jsn0a322687ee93',
    'X-RapidAPI-Host': ''
  });
  constructor(
    private http: HttpClient
  ) { }

  getTrendingTickers(): Observable<any>{
    this.header.set('X-RapidAPI-Host', 'apidojo-yahoo-finance-v1.p.rapidapi.com');
    return this.http.get<any>(`${this.YAHOO_FINANCE_URL}/get-trending-tickers?region=US`, {headers: this.header})
      .pipe(
      map((tickers) => {
        const extracted = tickers.finance.result[0].quotes;
        const symbols = extracted.map(s => s.symbol); // get symbols only
        const topThree = symbols.slice(0, 3); // get the top Three in the list
        return topThree.join(); // e.g "AMZN,GOOGL,TESLA"
      }),
      mergeMap(symbols =>
        this.http.get<any>(`${this.YAHOO_FINANCE_URL}/v2/get-quotes?region=US&symbols=${symbols}`, {headers: this.header})
          .pipe(map(detailedTickers => {
            let results = detailedTickers.quoteResponse.result;
            results = results.map(res => {
              // get symbol, change, changePercent, price only
              const shortTicker = {
                symbol: res.symbol,
                regularMarketChange: res.regularMarketChange,
                regularMarketChangePercent: res.regularMarketChangePercent,
                regularMarketPrice: res.regularMarketPrice
              };
              return shortTicker;
            });
            return results;
          }))
      ),
      mergeMap(tickers => {
        this.header.set('X-RapidAPI-Host', 'alpha-vantage.p.rapidapi.com');
        const firstBlock = this.http.get<any>(`${this.ALPHA_VANTAGE_URL}&symbol=${tickers[0].symbol}&outputsize=compact&datatype=json`, {headers: this.header})
          .pipe(
            map((data)=>this.convert(data,tickers[0]))
          );
        const secondBlock  = this.http.get<any>(`${this.ALPHA_VANTAGE_URL}&symbol=${tickers[1].symbol}&outputsize=compact&datatype=json`, {headers: this.header})
          .pipe(
            map((data)=>this.convert(data,tickers[1]))
          );;
        const thirdBlock = this.http.get<any>(`${this.ALPHA_VANTAGE_URL}&symbol=${tickers[2].symbol}&outputsize=compact&datatype=json`, {headers: this.header})
          .pipe(
            map((data)=>this.convert(data,tickers[2]))
          );;
        return forkJoin([firstBlock, secondBlock, thirdBlock]);
      })
    );
  }

  // convert Data to match with NgxChart format
  convert(data: any, ticker: any){
    const rawSeries = data['Time Series (Daily)'];
    if (rawSeries != null){
      const series = Object.keys(rawSeries).reverse().map(timestamp => {
        return {name: timestamp, value: rawSeries[timestamp]['4. close']};
      });
      const chartData = [];
      const details = Object.assign({
        name: ticker.symbol,
        series
      });
      chartData.push(details);
      let newObj = ticker;
      newObj.chart = chartData;
      return newObj;
    }
  }
}
