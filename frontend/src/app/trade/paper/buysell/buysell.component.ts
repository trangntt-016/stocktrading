import { Component, OnInit } from '@angular/core';
import { Order } from '../../../model/Order';

@Component({
  selector: 'app-buysell',
  templateUrl: './buysell.component.html',
  styleUrls: ['./buysell.component.css']
})
export class BuysellComponent implements OnInit {
  order: Order;
  constructor() { }

  ngOnInit(): void {
    this.order = new Order();
  }

  selectBuy() {
    this.order.orderSide = "BUY";
  }

  selectSell() {
    this.order.orderSide = "SELL";
  }

  submit(): void{
    console.log(this.order);
  }


}
