import {Symbol} from './Symbol';
import { OrderFilled } from './OrderFilled';

export class Order{
  orderId: number;
  userId: string;
  symbol: Symbol;
  orderSide: string;
  filledQuantity: number;
  filledTime: string;
  limitPrice: number;
  avgPrice: number;
  orderType: string;
  orderPlaced: string;
  orderStatus: string;
  newlyFilled: boolean;
  constructor(){
    this.orderId = null;
    this.symbol = null;
    this.orderSide = null;
    this.filledQuantity = 0;
    this.filledTime = null;
    this.limitPrice = 0;
    this.avgPrice = 0;
    this.orderType = 'LIMIT';
    this.orderPlaced = null;
    this.orderStatus = null;
    this.newlyFilled = false;
  }

  convertToOrder(o: OrderFilled):Order{
    this.symbol = new Symbol();

    this.orderId = o.orderId;
    this.symbol.symbol = o.symbol;
    this.symbol.name = o.name;
    this.symbol = this.symbol;
    this.symbol.name = o.name;
    this.orderSide = o.orderSide;
    this.filledQuantity = o.filledQuantity;

    this.filledTime = (o.filledTime=="null")?null:o.filledTime;
    this.limitPrice = o.limitPrice;
    this.avgPrice = o.limitPrice;
    this.orderType = o.orderType;
    this.orderPlaced = (o.orderPlaced=="null")?null:o.orderPlaced;
    this.orderStatus = o.orderStatus;
    this.newlyFilled = o.newlyFilled;
    return this;
  }
}
