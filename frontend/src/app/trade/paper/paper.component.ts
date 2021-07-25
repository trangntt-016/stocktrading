import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-paper',
  templateUrl: './paper.component.html',
  styleUrls: ['./paper.component.css']
})
export class PaperComponent implements OnInit {
  shouldShowOrder: boolean;
  constructor() { }

  ngOnInit(): void {
    this.shouldShowOrder = true;
  }

  showOrders(type: string): void{
    if (type === 'order'){
      this.shouldShowOrder = true;
    }
    else{
      this.shouldShowOrder = false;
    }
  }

}
