import { Component, OnInit } from '@angular/core';
import { single } from './data';

@Component({
  selector: 'app-test',
  templateUrl: './test.component.html',
  styleUrls: ['./test.component.css']
})
export class TestComponent implements OnInit {
  single: any[];
  public view: any[] = [700, 200];
  public showXAxis = false;
  public showYAxis = true;
  public gradient = false;
  public showLegend = true;
  public showXAxisLabel = true;
  public xAxisLabel: "Time";
  public showYAxisLabel = true;
    public yAxisLabel: "Populatoin";
  public graphDataChart: any[];
  public colorScheme = {
    domain: ['#5AA454', '#A10A28', '#C7B42C', '#AAAAAA']
  };
  constructor() {
    Object.assign(this, { single });
  }

  ngOnInit(): void {
  }

}
