import { Component, OnInit } from '@angular/core';
import { environment } from '../../../environments/environment';
import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';
import { MatSnackBar } from '@angular/material/snack-bar';
import { AuthService } from '../../service/auth.service';


@Component({
  selector: 'app-paper',
  templateUrl: './paper.component.html',
  styleUrls: ['./paper.component.css']
})
export class PaperComponent implements OnInit {
  private stompClient;
  private serverUrl = environment.stockWS;
  buyingPower: number;
  shouldShowOrder: boolean;


  constructor(
    private snackBar: MatSnackBar,
    private auth: AuthService
  ) {
  }

  ngOnInit(): void {
    this.shouldShowOrder = true;
    this.initializeWebSocketConnection();
  }

  showOrders(type: string): void {
    if (type === 'order') {
      this.shouldShowOrder = true;
    } else {
      this.shouldShowOrder = false;
    }
  }

  handleBuyingPower(event): void {
    this.buyingPower = event;
  }

  initializeWebSocketConnection(): any {
    console.log('connected to ws ...');

    const ws = new SockJS(this.serverUrl);

    this.stompClient = Stomp.over(ws);


    const that = this;

    this.stompClient.connect({}, (frame) => {
      that.stompClient.subscribe(`/queue/new-order`, (order) => {
        let userId = order.body.split(" ")[0];
        if (this.auth.readToken().userId != userId) {
          this.snackBar.open(order.body, "", {
            duration: 3000,
            verticalPosition: "top",
            horizontalPosition: "center"
          });
        }
      });
    }, (err) => {
      console.log(err);
    });
  }

}
