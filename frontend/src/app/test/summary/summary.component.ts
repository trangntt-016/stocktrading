import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { SummaryStock } from '../../model/SummaryStock';
import { DataManagerService } from '../../service/data-manager.service';

@Component({
  selector: 'app-summary',
  templateUrl: './summary.component.html',
  styleUrls: ['./summary.component.css']
})
export class SummaryComponent implements OnInit {
  summaryStocks: SummaryStock[];
  constructor(
    private dataService: DataManagerService
  ) { }

  ngOnInit(): void {

  }

}
