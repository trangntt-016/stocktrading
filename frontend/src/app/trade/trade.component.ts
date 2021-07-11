import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-trade',
  templateUrl: './trade.component.html',
  styleUrls: ['./trade.component.css']
})
export class TradeComponent implements OnInit {
  user: string;
  constructor() { }

  ngOnInit(): void {
    this.user = localStorage.getItem("userId");
  }

}
