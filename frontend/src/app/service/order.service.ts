import { EventEmitter, Injectable, Output } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { RegisterUser } from '../model/RegisterUser';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { map } from 'rxjs/operators';
import { Order } from '../model/Order';


@Injectable({
  providedIn: 'root'
})
export class OrderService {


  constructor(
    private http: HttpClient
  ) { }

  buysell(order: Order): Observable<any>{
    console.log(order);
    return this.http.post<any>(`${environment.orderAPI}`, order).pipe(map(res => res.data));
  }
}
