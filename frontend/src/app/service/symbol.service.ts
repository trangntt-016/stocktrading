import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Symbol } from '../model/Symbol';

@Injectable({
  providedIn: 'root'
})
export class SymbolService {
  constructor(
    private http: HttpClient
  ) { }

  getAllSymbols(): Observable<Symbol[]>{
    return this.http.get<any>('http://localhost:3000/api/symbols');
  }

}
