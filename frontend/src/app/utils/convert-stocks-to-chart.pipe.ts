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
  //  if(charts!=null){
  //    let converted = charts.map(
  //      chart => {
  //        const series = chart.series.map(s => {
  //          const date = new Date(s.date);
  //          const newDate = date.getUTCDate() + " " + date.toLocaleDateString('default',{month: 'long'});
  //          const seriesObj = Object.assign({
  //            name: newDate,
  //            value: s.close
  //          });
  //          return seriesObj;
  //        });
  //        const chartObj = Object.assign({
  //          name: chart.name,
  //          series: series
  //        });
  //        return chartObj;
  //      }
  //    );
  //    converted = converted.map(c => {
  //      return new Array(c);
  //    })
  //    return converted;
  //  }
  // }
}
