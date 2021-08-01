import { Pipe, PipeTransform } from '@angular/core';

@Pipe(
  {name: 'convertStocksToChart'}
)
export class ConvertStocksToChartPipe implements PipeTransform {
  transform(value: any): any {
    console.log(value);
    return value;
  }
}
