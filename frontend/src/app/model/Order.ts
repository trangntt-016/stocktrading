import {Symbol} from './Symbol';

export interface Order {
  orderId: null;
  symbol: Symbol;
  orderSide: string;
  filledQuantity: number;
  filledTime: Date;
  limitPrice: number;
  avgPrice: number;
  orderType: string;
  orderPlaced: null;
  orderStatus: null;

}
