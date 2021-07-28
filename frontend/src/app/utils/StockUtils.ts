import { DailyDetails } from '../model/DailyDetails';
import { DailyBidAsk } from '../model/DailyBidAsk';

export class StockUtils{
  public setIntervalImmediately(func, interval: number){
    func;
    console.log(interval);
    return setInterval(func, interval);
  }

  public convertToDaily(newMes): DailyDetails {
    let daily = new DailyDetails();
    newMes = newMes.replace("[","").replace("]","");
    let processed = newMes.split(",");
    processed.forEach(val => {
      const array = val.split(":");
      const key = array[0].replace(",","");
      const value = array[1].replace(",","");
      switch(key){
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
    let daily = new DailyBidAsk();

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

}
