import { EventEmitter, Injectable, Output } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Watchlist } from '../model/Watchlist';
import { Daily } from '../model/Daily';
import {Symbol} from '../model/Symbol';
import { map } from 'rxjs/operators';
import { News } from '../model/News';
@Injectable({
  providedIn: 'root'
})
export class WatchlistService {
  header = new HttpHeaders({
    'X-RapidAPI-Key': 'cc9949f803msh353c32ef5d787efp14bdb8jsn98e795594911',
    'X-RapidAPI-Host': 'yahoo-finance15.p.rapidapi.com'
  });

  @Output()watchlistEvt: EventEmitter<Watchlist[]> = new EventEmitter();
  @Output()selectedWatchlistEvt: EventEmitter<Watchlist> = new EventEmitter<Watchlist>();
  @Output()selectedDailyEvt: EventEmitter<Daily> = new EventEmitter<Daily>();

  sendWatchlists(watchlists: Watchlist[]): any{
    this.watchlistEvt.emit(watchlists);
  }

  sendSelectedWatchlist(selected: Watchlist): any{
    this.selectedWatchlistEvt.emit(selected);
  }

  sendSelectedDaily(selected: Daily): any{
    this.selectedDailyEvt.emit(selected);
  }


  constructor(
    private http: HttpClient
  ) { }

  getAllWatchlistsByUserId(userId: string): Observable<Watchlist[]>{
    return this.http.get<any>(`http://localhost:3000/api/watchlists?userId=${userId}`);
  }

  updateAWatchlist(watchlist: Watchlist): Observable<any>{
    return this.http.put<any>('http://localhost:3000/api/watchlists', watchlist);
  }

  deleteAWatchlist(watchlistId: number): Observable<any>{
    return this.http.delete<any>(`http://localhost:3000/api/watchlists/${watchlistId}`);
  }

  createAWatchlist(userId: string, watchlistName: string): Observable<any>{
    return this.http.post<string>(`http://localhost:3000/api/watchlists?userId=${userId}`,watchlistName);
  }

  findAllDailiesByWatchlistId(watchlistId: number): Observable<any>{
    return this.http.get<Daily>(`http://localhost:3000/api/watchlists/${watchlistId}/dailies`);
  }

  deleteASymbolFromWatchlistId(watchlistId: number, symbolId: number): Observable<any>{
    return this.http.delete<Daily>(`http://localhost:3000/api/watchlists/${watchlistId}/symbols/${symbolId}`);
  }

  getNewsBySymbol(symbol: Symbol): Observable<News[]>{
      return this.http.get<any>(`https://yahoo-finance15.p.rapidapi.com/api/yahoo/ne/news/${symbol.symbol}`,{headers: this.header})
        .pipe(
          map(news => news.item)
        )
  }
}
