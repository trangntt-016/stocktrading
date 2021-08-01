import { Pipe, PipeTransform } from '@angular/core';

@Pipe(
  {name: 'formatVolume'}
)
export class FormatVolumePipe implements PipeTransform {
  transform(value: any, ...args: any[]): any {
    if (value / 1000000 >= 1) {
      const result = value / 1000000;
      return result.toFixed(2) + ' M';
    } else {
      const result = value / 100000;
      return result.toFixed(2) + ' K';
    }
  }

}
