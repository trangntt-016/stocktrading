import { EventEmitter, Injectable, Output } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Watchlist } from '../../model/Watchlist';

@Injectable({
  providedIn: 'root'
})
export class WatchlistService {
  @Output()watchlistEvt: EventEmitter<Watchlist[]> = new EventEmitter();

  sendWatchlists(watchlists: Watchlist[]): any{
    this.watchlistEvt.emit(watchlists);
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
}
