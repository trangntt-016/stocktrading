import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Symbol } from '../model/Symbol';

import { environment } from 'src/environments/environment';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class SymbolService {
  constructor(
    private http: HttpClient
  ) { }

  getAllSymbols(): Observable<Symbol[]>{
    return this.http.get<any>(`${environment.symbolAPI}`).pipe(map(res => res.data));
  }

}
