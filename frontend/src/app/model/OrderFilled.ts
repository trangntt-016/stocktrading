
export class OrderFilled{
  orderId: number;
  userId: string;
  symbol: string;
  name: string;
  orderSide: string;
  filledQuantity: number;
  filledTime: string;
  limitPrice: number;
  avgPrice: number;
  orderType: string;
  orderPlaced: string;
  orderStatus: string;
  constructor(){
    this.orderId = null;
    this.symbol = '';
    this.name = '';
    this.orderSide = '';
    this.filledQuantity = 0;
    this.filledTime = null;
    this.limitPrice = 0;
    this.avgPrice = 0;
    this.orderType = '';
    this.orderPlaced = null;
    this.orderStatus = '';
  }

}
