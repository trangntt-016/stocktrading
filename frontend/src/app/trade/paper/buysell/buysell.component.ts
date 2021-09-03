import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { Order } from '../../../model/Order';
import { Symbol } from '../../../model/Symbol';
import { SymbolService } from '../../../service/symbol.service';
import { OrderService } from '../../../service/order.service';
import { environment } from '../../../../environments/environment';
import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';
import { StockUtils } from '../../../utils/StockUtils';
import { DailyBidAsk } from '../../../model/DailyBidAsk';
import { Subscription } from 'rxjs';
import { AuthService } from '../../../service/auth.service';
import { MatSnackBar } from '@angular/material/snack-bar';


@Component({
  selector: 'app-buysell',
  templateUrl: './buysell.component.html',
  styleUrls: ['./buysell.component.css']
})
export class BuysellComponent implements OnInit, OnDestroy {
  @Input() buyingPower: number;
  private subscription: Subscription;
  private serverUrl = environment.stockWS;
  private stompClient;
  private stompClientSub;
  private utils: StockUtils;
  private userId: string;

  amount = 0;
  order: Order;
  symbols: Symbol[];
  autoSymbols: Symbol[];
  searchSymbol = " ";
  dailyBidAsk: DailyBidAsk;
  matchedPrice: number;
  showMatched: boolean;
  loading = true;


  constructor(
    private symbolService: SymbolService,
    private orderService: OrderService,
    private authService: AuthService,
    private snackBar: MatSnackBar
  ) {
  }

  ngOnInit(): void {
    this.utils = new StockUtils();
    this.order = new Order();
    this.userId = this.authService.readToken().userId;

    this.subscription = this.symbolService.getAllSymbols().subscribe(s => {
      this.symbols = s;
      this.autoSymbols = s;
      this.searchSymbol = s[0].symbol;
      this.symbolService.sendSelectedSymbol(s[0]);
      this.initializeWebSocketConnection(s[0].symbolId);
    });

    this.symbolService.selectedSymbolEvt.subscribe(symbol => {
      if (this.stompClient !== undefined) {
        this.searchSymbol = symbol.symbol;
        this.subscription = this.stompClient.subscribe(`/user/${this.userId}/topic/bid-ask`, (daily) => {
          this.dailyBidAsk = this.utils.convertToDailyBidAsk(daily.body);
        });
        this.stompClient.subscribe(`/user/${this.userId}/topic/matched-price`, (matched) => {
          this.matchedPrice = matched.body;
        });
        this.stompClient.send(`/app/bid-ask`, {}, ('userId:' + this.userId + ',symbolId:' + symbol.symbolId));
      }
    });
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  selectBuy(): void {
    this.order.orderSide = 'BUY';
  }

  selectSell(): void {
    this.order.orderSide = 'SELL';
  }

  submit(): void {
    const date = new Date();

    const symbol = this.symbols.filter(s => s.symbol === this.searchSymbol)[0];

    this.order.symbol = symbol;

    this.order.userId = this.userId;

    // if (date.getHours() < 9 || date.getHours() > 16 || date.getHours() === 9 && date.getMinutes() <= 30 || date.getHours() === 16 && date.getMinutes() > 0) {
    //   this.showError("Market is closing. Please revisit during 9:30am - 4:00pm.");
    //   return;
    // }
    if(this.order.orderSide == null) {
      this.showError('Please Select A Side.');
    }
    if(this.order.filledQuantity == 0){
      this.showError('Filled Quantity Should Not Be 0.')
    }
    else {
      this.subscription = this.orderService.buysell(this.order).subscribe(order => {
        this.amount = order.filledQuantity * order.limitPrice;
        this.orderService.sendSelectedOrder(order);
      });
    }
  }

  doFilter(): void {
    this.autoSymbols = this.filter(this.symbols);
  }

  filter(values): any {
    return values.filter(symbol =>
      symbol.symbol.toUpperCase().includes(this.searchSymbol.toUpperCase()))
  }

  showMatchedPrice(): void {
    this.showMatched = !this.showMatched;
  }

  initializeWebSocketConnection(symbolId: number): any {
    console.log('connected to ws Buy Sell component...');

    const ws = new SockJS(this.serverUrl);

    this.stompClient = Stomp.over(ws);

    this.stompClientSub = Stomp.over(ws);

    const copyStompClient = this.stompClient;

    const that = this;

    this.stompClient.connect({}, (frame) => {
      that.subscription = copyStompClient.subscribe(`/user/${this.userId}/topic/bid-ask`, (daily) => {
        that.dailyBidAsk = that.utils.convertToDailyBidAsk(daily.body);
      });
      that.subscription = copyStompClient.subscribe(`/user/${this.userId}/topic/matched-price`, (matched) => {
        that.matchedPrice = matched.body;
      });
      copyStompClient.send(`/app/bid-ask`, {}, ('userId:' + this.userId + ',symbolId:' + symbolId));
    }, (err) => {
      console.log(err);
    });
  }

  selectSymbol(symbol): void{
    if (symbol !== undefined) {
      this.stompClient.send(`/app/bid-ask`, {}, ("userId:"+this.userId+",symbolId:"+symbol.symbolId));

      this.symbolService.sendSelectedSymbol(symbol);

      this.matchedPrice = null;

      this.showMatched = false;

    }
  }

  showError(content:string) :void{
    this.snackBar.open(content, "Got it!", {
      duration: 3000,
      verticalPosition: "top",
      horizontalPosition: "center"
    });
  }
}
