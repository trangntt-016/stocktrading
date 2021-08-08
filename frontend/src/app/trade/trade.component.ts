import { Component, OnInit } from '@angular/core';
import { AuthService } from '../service/auth.service';
import { FormatDatePipe } from '../utils/format-date.pipe';

@Component({
  selector: 'app-trade',
  templateUrl: './trade.component.html',
  styleUrls: ['./trade.component.css']
})
export class TradeComponent implements OnInit {
  dateUtils: FormatDatePipe;

  user: string;
  constructor(
    private authService: AuthService
  ) { }

  ngOnInit(): void {
    this.user = this.authService.readToken().userId;
    this.dateUtils = new FormatDatePipe();
  }

  logout(): void {
    this.authService.logout();
  }

  simulationTime(): any {
    return this.dateUtils.convertedHourMinsSecondsAmPm();
  }

  isOpen(): boolean {
    const date = new Date();
    if (date.getHours() < 9 || date.getHours() > 16 || date.getHours() === 9 && date.getMinutes() <= 30 || date.getHours() === 16 && date.getMinutes() > 0) {
      return false;
    }
    return true;
  }

}
