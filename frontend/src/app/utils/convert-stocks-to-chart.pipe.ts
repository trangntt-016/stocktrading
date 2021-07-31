import { Pipe, PipeTransform } from '@angular/core';
import { ChartDto } from '../model/HistoricalQuoteDto';

@Pipe(
  {name: 'convertStocksToChart'}
)
export class ConvertStocksToChartPipe implements PipeTransform{
  transform(value: any): any {
    console.log(value);
    return value;
  }
}
