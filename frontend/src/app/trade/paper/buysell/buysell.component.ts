import { Component, OnInit } from '@angular/core';
import { Order } from '../../../model/Order';
import { Symbol } from '../../../model/Symbol';
import { SymbolService } from '../../../service/symbol.service';
import { OrderService } from '../../../service/order.service';
import { environment } from '../../../../environments/environment';
import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';
import { StockUtils } from '../../../utils/StockUtils';
import { DailyBidAsk } from '../../../model/DailyBidAsk';


@Component({
  selector: 'app-buysell',
  templateUrl: './buysell.component.html',
  styleUrls: ['./buysell.component.css']
})
export class BuysellComponent implements OnInit {
  order: Order;
  symbols: Symbol[];
  autoSymbols: Symbol[];
  searchSymbol: string;
  daily: DailyBidAsk;
  matchedPrice: number;
  showMatched: boolean;
  private serverUrl = environment.stockWS;
  private stompClient;
  private stompClientSub;
  private utils: StockUtils;


  constructor(
    private symbolService: SymbolService,
    private orderService: OrderService
  ) { }

  ngOnInit(): void {
    this.utils = new StockUtils();
    this.order = new Order();

    this.symbolService.getAllSymbols().subscribe(s => {
      this.symbols = s;
      this.autoSymbols = s;
      this.searchSymbol = s[0].symbol;
      this.symbolService.sendSelectedSymbol(s[0]);
      this.initializeWebSocketConnection(s[0].symbolId);
    });


    this.symbolService.selectedSymbolEvt.subscribe(symbol => {
      this.stompClient.subscribe(`/topic/trade/${symbol.symbolId}`, (daily) => {
        this.daily = this.utils.convertToDailyBidAsk(daily.body);
      });
      this.stompClient.subscribe(`/topic/trade/${symbol.symbolId}/future`, (matched) => {
        this.matchedPrice = matched.body;
      });
      this.stompClient.send(`/app/trade/${symbol.symbolId}`, {}, (""));
    });
  }

  selectBuy(): void {
    this.order.orderSide = 'BUY';
  }

  selectSell(): void {
    this.order.orderSide = 'SELL';
  }

  submit(): void {
    const symbol = this.symbols.filter(s => s.symbol === this.searchSymbol)[0];

    this.order.symbol = symbol;

    this.order.userId = "U_004";
    console.log(this.order);

    this.orderService.buysell(this.order).subscribe(order => {
      this.orderService.sendSelectedOrder(order);
    })
  }

  doFilter(): void{
    this.autoSymbols = this.filter(this.symbols);
    const symbol = this.symbols.filter(s => s.symbol === this.searchSymbol)[0];
    if (symbol !== undefined) {
      this.symbolService.sendSelectedSymbol(symbol);
    }
  }

  filter(values): any{
    return values.filter(symbol => {
      return symbol.symbol.toUpperCase().includes(this.searchSymbol.toUpperCase());
    });
  }

  showMatchedPrice(): void{
    this.showMatched = !this.showMatched;
  }

  initializeWebSocketConnection(symbolId: number): any {
    console.log('connected to ws ...');

    const ws = new SockJS(this.serverUrl);
    this.stompClient = Stomp.over(ws);
    this.stompClientSub = Stomp.over(ws);

    const copyStompClient = this.stompClient;
    // it helps to render data on html
    const that = this;

    this.stompClient.connect({}, function(frame) {
      copyStompClient.subscribe(`/user/U_004/topic/trade/${symbolId}`, (daily) => {
        that.daily = that.utils.convertToDailyBidAsk(daily.body);
        console.log(that.daily);
      });
      copyStompClient.subscribe(`/topic/trade/${symbolId}/future`, (matched) => {
        that.matchedPrice = matched.body;
      });
      copyStompClient.send(`/app/trade/${symbolId}`, {}, (""));
    }, (err) => {
      console.log(err);
    });
  }


}
