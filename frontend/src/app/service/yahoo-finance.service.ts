import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class YahooFinanceService {

  constructor(
    private http: HttpClient
  ) { }

  getHistoricalQuotes(symbol: string, interval: number): Observable<any>{
    return this.http.get<any>(`${environment.yahooAPI}?symbol=${symbol}&interval=${interval}`).pipe(map((res) => {
      const rawData = res.data;

      let data = new Array();

      rawData.forEach(raw => {
        const object = Object.assign({
          x: new Date(raw.date).getTime(),
          y: Array.of(raw.open.toFixed(2), raw.high.toFixed(2), raw.low.toFixed(2), raw.close.toFixed(2))
        });
        data.push(object);
      })

      return Object.assign({
        series: [Object.assign({
          name: "candle",
          data: data
        })],
        chart: {
          type: "candlestick",
          height: 310,
          width: 770
        },
        xaxis: {
          type: "datetime"
        },
        yaxis: {
          tooltip: {
            enabled: true
          }
        }
      });
    }));
  }

}
