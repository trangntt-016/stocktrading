
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
  newlyFilled: boolean;
  constructor(){  }

}
