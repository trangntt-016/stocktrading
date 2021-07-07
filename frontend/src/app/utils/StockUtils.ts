export class StockUtils{
  public setIntervalImmediately(func, interval: number){
    func;
    console.log(interval);
    return setInterval(func, interval);
  }
}
