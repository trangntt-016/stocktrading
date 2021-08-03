import { DailyDetails } from '../model/DailyDetails';
import { DailyBidAsk } from '../model/DailyBidAsk';
import { Order } from '../model/Order';
import { OrderFilled } from '../model/OrderFilled';
import { DailyPriceChange } from '../model/DailyPriceChange';
import { Position } from '../model/Position';
import { Account } from '../model/Account';

export class StockUtils {
  public setIntervalImmediately(func, interval: number) {
    func;
    console.log(interval);
    return setInterval(func, interval);
  }

  public convertToDaily(newMes): DailyDetails {
    const daily = new DailyDetails();

    newMes = newMes.replace('[', '').replace(']', '');

    const processed = newMes.split(',');

    processed.forEach(val => {
      const array = val.split(':');
      const key = array[0].replace(',', '');
      const value = array[1].replace(',', '');
      switch (key) {
        case 'dailyId':
          daily.dailyId = +value;
          break;
        case 'date':
          daily.date = value;
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
    const returnedOrder = [];

    order = order.replace('[', '').replace(']', '');

    const processed = order.split('OrderFilledDto(');

    processed.shift();

    processed.forEach(o => {
      const orderArr = o.split(',');

      if (orderArr[orderArr.length - 1] == ' ') {
        orderArr.pop();
      }

      if (!orderArr[3].includes('=')) {
        orderArr[2] = orderArr[2] + ',' + orderArr[3];
        orderArr.splice(3, 1);
      }

      orderArr[orderArr.length - 1] = orderArr[orderArr.length - 1].replace(')', '');

      const orderFilled = new OrderFilled();

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
          case 'newlyFilled':
            orderFilled.newlyFilled = (value === 'true');
            break;
          default:
            null;
        }
      });
      order = order.convertToOrder(orderFilled);
      returnedOrder.push(order);
    });
    return returnedOrder;
  }

  convertToDailyPriceChange(dailies: String): DailyPriceChange[] {
    const dailiesPriceChange = [];

    dailies = dailies.replace('[', '').replace(']', '');

    const processed = dailies.split('DailyDtoPriceChange(');

    processed.shift();

    processed.forEach(d => {
      const dailyPriceChage = new DailyPriceChange();

      const dailiesArr = d.split(',');

      if (dailiesArr[dailiesArr.length - 1] == ' ') {
        dailiesArr.pop();
      }

      dailiesArr[dailiesArr.length - 1] = dailiesArr[dailiesArr.length - 1].replace(')', '');

      dailiesArr.forEach(data => {
        data = data.trim();

        const keyvalue = data.split('=');

        const key = keyvalue[0];

        const value = keyvalue[1];

        switch (key) {
          case 'symbol':
            dailyPriceChage.symbol = value;
            break;
          case 'price':
            dailyPriceChage.price = +value;
            break;
          case 'change':
            dailyPriceChage.change = +value;
            break;
          case 'changeInPercent':
            dailyPriceChage.changeInPercent = +value;
            break;
          case 'prevClose':
            dailyPriceChage.prevClose = +value;
            break;
          default:
            null;
        }
      });

      dailiesPriceChange.push(dailyPriceChage);
    });
    return dailiesPriceChange;
  }

  convertToPositions(positions: String): Position[] {
    const positionsArr = [];

    positions = positions.replace('[', '').replace(']', '');

    const processed = positions.split('PositionDto(');

    processed.shift();

    processed.forEach(p => {
      const position = new Position();

      const posArr = p.split(',');

      if (posArr[posArr.length - 1] == ' ') {
        posArr.pop();
      }

      if (!posArr[3].includes('=')) {
        posArr[2] = posArr[2] + ',' + posArr[3];
        posArr.splice(3, 1);
      }

      posArr[posArr.length - 1] = posArr[posArr.length - 1].replace(')', '');

      posArr.forEach(data => {
        data = data.trim();

        const keyvalue = data.split('=');

        const key = keyvalue[0];

        const value = keyvalue[1];

        switch (key) {
          case 'symbolId':
            position.symbolId = +value;
            break;
          case 'symbol':
            position.symbol = value;
            break;
          case 'name':
            position.name = value;
            break;
          case 'filledQuantity':
            position.filledQuantity = +value;
            break;
          case 'lastPrice':
            position.lastPrice = +value;
            break;
          case 'marketValue':
            position.marketValue = +value;
            break;
          case 'avgPrice':
            position.avgPrice = +value;
            break;
          case 'totalCost':
            position.totalCost = +value;
            break;
          case 'unrealizedPL':
            position.unrealizedPL = +value;
            break;
          case 'unrealizedPLPercent':
            position.unrealizedPLPercent = +value;
            break;
          case 'type':
            position.type = value;
            break;
          default:
            null;
        }
      });
      positionsArr.push(position);
    });
    return positionsArr;
  }

  convertToAccount(acc: String): Account {
    const processed = acc.split('Account(');

    processed.shift();

    const processedAcc = processed[0];

    const account = new Account();

    const accArr = processedAcc.split(',');

    accArr[accArr.length - 1] = accArr[accArr.length - 1].replace(')', '');

    accArr.forEach(data => {
      data = data.trim();

      const keyvalue = data.split('=');

      const key = keyvalue[0];

      const value = keyvalue[1];

      switch (key) {
        case 'userId':
          account.userId = value;
          break;
        case 'initialValue':
          account.initialValue = +value;
          break;
        case 'netAccountValue':
          account.netAccountValue = +value;
          break;
        case 'overallPL':
          account.overallPL = +value;
          break;
        case 'PLChange':
          account.PLChange = +value;
          break;
        case 'marketValue':
          account.marketValue = +value;
          break;
        case 'buyingPower':
          account.buyingPower = +value;
          break;
        default:
          null;
      }
    });
    return account;
  }

}
