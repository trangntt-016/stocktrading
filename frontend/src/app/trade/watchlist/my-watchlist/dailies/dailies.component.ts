import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-dailies',
  templateUrl: './dailies.component.html',
  styleUrls: ['./dailies.component.css']
})
export class DailiesComponent implements OnInit {
  // displayedColumns: string[] = [' ', 'Symbol', 'Name', 'Price','Change', '% Change', 'Open', 'PrevClose', 'High', 'Low','Volumne','Exchange'];
  displayedColumns: string[] = ['delete', 'symbol', 'name', 'change', 'percentChange','open','prevclose', 'high','low','volume','exchange'];
  ELEMENT_DATA: any[] = [
    {remove:'', symbol: 'Hydrogen', name: 1.0079, change: 'fr', percentChange:'22',open: 'H',prevclose: '23.3', high: '99.3', low: '22', volume: '22', exchange: 'NASDAQ'}
  ];

  dataSource = this.ELEMENT_DATA;
  constructor() { }

  ngOnInit(): void {
  }

}
