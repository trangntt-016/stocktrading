import { Pipe, PipeTransform } from '@angular/core';

@Pipe(
  {name: 'formatDate'}
)
export class FormatDatePipe implements PipeTransform {
  transform(value: any, ...args: any[]): any {
    const dateOn = new Date(value);

    let result = '';

    switch (this.typeOfDate(dateOn)){
      case -1: {
        result = 'Yesterday, ' + this.convertedHourMinsAmPm(dateOn);
        break;
      }
      case 0: {
        result = 'Today, ' + this.convertedHourMinsAmPm(dateOn);
        break;
      }
      default: {
        result = this.convertedMonth(dateOn.getMonth()) + ' ' + this.convertedDay(dateOn.getDate()) + ', ' + dateOn.getFullYear();
      }
    }
    return result;
  }

  public typeOfDate(date: Date): number{
    const convertedDate = new Date(date);
    const today = new Date();
    const yesterday = new Date(Date.now() - 864e5);

    const Today = {
      date: today.getDate(),
      month: today.getUTCMonth(),
      year: today.getFullYear()
    };
    const Yesterday = {
      date: yesterday.getDate(),
      month: yesterday.getUTCMonth(),
      year: yesterday.getFullYear()
    };
    const CheckedDate = {
      date: convertedDate.getDate(),
      month: convertedDate.getUTCMonth(),
      year: convertedDate.getFullYear()
    };

    if (CheckedDate.date == Yesterday.date && CheckedDate.month == Yesterday.month && CheckedDate.year == Yesterday.year){
      return -1;
    }
    else if (CheckedDate.date == Today.date && CheckedDate.month == Today.month && CheckedDate.year == Today.year){
      return 0;
    }
    return 1;
  }

  public convertedDay(day: number): string{
    return day < 10 ? ('0' + day.toString()) : day.toString();
  }

  public convertedHourMinsAmPm(convertedDate: Date): string{
    let hour = '';
    let minutes = '';
    let pmAm = '';
    hour = (convertedDate.getHours() > 12) ? (convertedDate.getHours() - 12).toString() : convertedDate.getHours().toString();
    if (parseInt(hour) < 10){
      hour = '0' + hour;
    }
    minutes = (convertedDate.getMinutes() < 10) ? ('0' + convertedDate.getMinutes().toString()) : convertedDate.getMinutes().toString();

    pmAm = (convertedDate.getHours() > 12) ? 'pm' : 'am';
    return hour + ':' + minutes + pmAm;
  }

  public convertedMonth(monthIdx: number): string{
    const month = [
      'January',
      'February',
      'March',
      'April',
      'May',
      'June',
      'July',
      'August',
      'September',
      'October',
      'November',
      'December'
    ];
    return month[monthIdx];
  }
}
