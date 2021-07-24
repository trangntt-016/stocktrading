import {Symbol} from './Symbol';

export class Order{
  orderId?: Number;
  symbol?: Symbol;
  orderSide?: String;
  filledQuantity?: number;
  filledTime?: Date;
  limitPrice?: number;
  avgPrice?: number;
  orderType?: String;
  orderPlaced?: string;
  orderStatus?: string;
  constructor(){
    this.orderId = null;
    this.symbol = null;
    this.orderSide = null;
    this.filledQuantity = 0;
    this.filledTime = null;
    this.limitPrice = 0;
    this.avgPrice = 0;
    this.orderType = "LIMIT";
    this.orderPlaced = null;
    this.orderStatus = null;
  }
}
