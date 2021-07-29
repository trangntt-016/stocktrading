import { DailyDetails } from '../model/DailyDetails';
import { DailyBidAsk } from '../model/DailyBidAsk';
import { Order } from '../model/Order';
import { OrderFilled } from '../model/OrderFilled';

export class StockUtils {
  public setIntervalImmediately(func, interval: number) {
    func;
    console.log(interval);
    return setInterval(func, interval);
  }

  public convertToDaily(newMes): DailyDetails {
    let daily = new DailyDetails();
    newMes = newMes.replace('[', '').replace(']', '');
    let processed = newMes.split(',');
    processed.forEach(val => {
      const array = val.split(':');
      const key = array[0].replace(',', '');
      const value = array[1].replace(',', '');
      switch (key) {
        case 'dailyId':
          daily.dailyId = +value;
          break;
        case 'timestamp':
          daily.timestamp = value;
          break;
        case 'price':
          daily.price = +value;
          break;
        case 'open':
          daily.open = +value;
          break;
        case 'high':
          daily.high = +value;
          break;
        case 'low':
          daily.low = +value;
          break;
        case 'close':
          daily.close = +value;
          break;
        case 'volume':
          daily.volume = +value;
          break;
        case 'avg_vol_3_months':
          daily.avg_vol_3_months = +value;
          break;
        case 'prev_close':
          daily.prevClose = +value;
          break;
        case 'week_high_52':
          daily.week_high_52 = +value;
          break;
        case 'week_low_52':
          daily.week_low_52 = +value;
          break;
        case 'symbol':
          daily.symbol = value;
          break;
        case 'change':
          daily.change = value;
          break;
        case 'changeInPercent':
          daily.changeInPercent = value;
          break;
        default:
          null;
      }
    });
    return daily;
  }

  public convertToDailyBidAsk(newMes): DailyBidAsk {
    const daily = new DailyBidAsk();

    newMes = newMes.replace('[', '').replace(']', '');

    const processed = newMes.split(',');
    processed.forEach(val => {
      const array = val.split(':');

      const key = array[0].replace(',', '');

      const value = array[1].replace(',', '');

      switch (key) {
        case 'bid':
          daily.bid = +value;
          break;
        case 'ask':
          daily.ask = value;
          break;
        case 'spread':
          daily.spread = +value;
          break;
        default:
          null;
      }
    });
    return daily;
  }

  public convertToOrders(order): Order[] {
    const returnedOrder = new Array();

    order = order.replace('[', '').replace(']', '');

    const processed = order.split('OrderFilledDto(');

    processed.shift();

    processed.forEach(o => {
      const orderArr = o.split(',');

      orderArr.pop();

      if (!orderArr[3].includes('=')) {
        orderArr[2] = orderArr[2] + ',' + orderArr[3];
      }
      orderArr.splice(3, 1);

      orderArr[orderArr.length - 1] = orderArr[orderArr.length - 1].replace(')','');

      let orderFilled = new OrderFilled();

      let order = new Order();
      orderArr.forEach(data => {
        data = data.trim();

        const keyvalue = data.split('=');
        const key = keyvalue[0];
        const value = keyvalue[1];
        switch (key) {
          case 'orderId':
            orderFilled.orderId = +value;
            break;
          case 'symbol':
            orderFilled.symbol = value;
            break;
          case 'name':
            orderFilled.name = value;
            break;
          case 'orderSide':
            orderFilled.orderSide = value;
            break;
          case 'filledQuantity':
            orderFilled.filledQuantity = +value;
            break;
          case 'filledTime':
            orderFilled.filledTime = value;
            break;
          case 'limitPrice':
            orderFilled.limitPrice = +value;
            break;
          case 'avgPrice':
            orderFilled.avgPrice = +value;
            break;
          case 'orderType':
            orderFilled.orderType = value;
            break;
          case 'orderPlaced':
            orderFilled.orderPlaced = value;
            break;
          case 'orderStatus':
            orderFilled.orderStatus = value;
            break;
          default:
            null;
        }
      });
      console.log(orderFilled);
      order = order.convertToOrder(orderFilled);
      returnedOrder.push(order);
    });
    return returnedOrder;
  }

}
