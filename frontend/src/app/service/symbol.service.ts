import { EventEmitter, Injectable, Output } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Symbol } from '../model/Symbol';

import { environment } from 'src/environments/environment';
import { map } from 'rxjs/operators';
import { Watchlist } from '../model/Watchlist';

@Injectable({
  providedIn: 'root'
})
export class SymbolService {
  @Output()selectedSymbolEvt: EventEmitter<Symbol> = new EventEmitter<Symbol>();

  constructor(
    private http: HttpClient
  ) { }

  getAllSymbols(): Observable<Symbol[]>{
    return this.http.get<any>(`${environment.symbolAPI}`).pipe(map(res => res.data));
  }

  sendSelectedSymbol(selected: Symbol): any{
    this.selectedSymbolEvt.emit(selected);
  }

}
