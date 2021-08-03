import { EventEmitter, Injectable, Output } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { map } from 'rxjs/operators';
import { Order } from '../model/Order';


@Injectable({
  providedIn: 'root'
})
export class OrderService {
  @Output()selectedOrderEvt: EventEmitter<Order> = new EventEmitter<Order>();

  constructor(
    private http: HttpClient
  ) { }

  buysell(order: Order): Observable<any>{
    return this.http.post<any>(`${environment.orderAPI}`, order).pipe(map(res => res.data));
  }

  sendSelectedOrder(selected: Order): any{
    this.selectedOrderEvt.emit(selected);
  }

  getAllOrdersByUserId(userId: string): Observable<any>{
    return this.http.get<any>(`${environment.orderAPI}?userId=${userId}`).pipe(map(res => res.data));
  }
}
