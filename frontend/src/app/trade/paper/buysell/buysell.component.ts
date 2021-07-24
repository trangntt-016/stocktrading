import { Component, OnInit } from '@angular/core';
import { Order } from '../../../model/Order';
import { Observable } from 'rxjs';
import { Symbol } from '../../../model/Symbol';
import { SymbolService } from '../../../service/symbol.service';
import { map } from 'rxjs/operators';
import {ViewEncapsulation} from '@angular/core';

@Component({
  selector: 'app-buysell',
  templateUrl: './buysell.component.html',
  styleUrls: ['./buysell.component.css']
})
export class BuysellComponent implements OnInit {
  order: Order;
  symbols: Symbol[];
  searchSymbol: string;

  constructor(
    private symbolService: SymbolService
  ) { }

  ngOnInit(): void {
    this.order = new Order();

    this.symbolService.getAllSymbols().subscribe(s => {
      this.symbols = s;
    })
  }

  selectBuy() {
    this.order.orderSide = 'BUY';
  }

  selectSell() {
    this.order.orderSide = 'SELL';
  }

  submit(): void{
    const symbol = this.symbols.filter(s => s.symbol === this.searchSymbol)[0];
    this.order.symbol = symbol;
  }

  doFilter(){
    this.filter(this.symbols);
  }

  filter(values): any{
    return values.filter(symbol => {
      return symbol.symbol.toUpperCase().includes(this.searchSymbol.toUpperCase());
    });
  }


}
