//  npm i net -S to solve bug cannot resolve net ...
// (window as any).global = window in polyfill.ts to solve global uncaught reference ...

import { Component, OnInit, ViewChild } from '@angular/core';
import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';
import { NgForm } from '@angular/forms';
import { SocialAuthService, SocialUser } from 'angularx-social-login';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { WatchlistService } from '../service/watchlist.service';
import { Daily } from '../model/Daily';

declare var FB: any;
@Component({
  selector: 'app-test',
  templateUrl: './test.component.html',
  styleUrls: ['./test.component.css']
})
export class TestComponent implements OnInit{
//   message;
//   symbol;
//   private stompClient;
//   private stompClientSub;
//   private serverUrl = "http://localhost:3000/questions";
//   ngOnInit(): void{
//     this.initializeWebSocketConnection();
//     this.message = '';
//   }
//
//   initializeWebSocketConnection(): void {
//
//     console.log('connected to ws ...');
//
//     const ws = new SockJS(this.serverUrl);
//     this.stompClient = Stomp.over(ws);
//     this.stompClientSub = Stomp.over(ws);
//
//     const copyStompClient = this.stompClient;
//     this.stompClient.connect({}, function(frame) {
// //         copyStompClient.subscribe(`/topic/questions`, (newMes) => {
// // //          console.log("new symbol"+ this.symbol);
// //           console.log("new mes"+ newMes);
// //         });
//     }, (err) => {
//       console.log(err);
//     });
//   }
//
//   AMZN(message: string){
//     // this.stompClient.subscribe(`/topic/questions/AMZN`, (newMes) => {
//     //   console.log("is subscribing to AMZN"+ newMes);
//     // });
//     // this.stompClient.send("app/questions/AMZN",{},(message));
//      //this.stompClient.send("/app/questions",{},(message));
//     this.stompClient.unsubscribe();
//     this.stompClient.subscribe(`/topic/AMZN`, (newMes) => {
//       console.log("is subscribing to AMZN"+ newMes);
//     });
//     this.stompClient.send("/app/AMZN",{},(message));
//   }
//
//   GOOGL(message: string){
//     this.stompClient.unsubscribe();
//     this.stompClient.subscribe(`/topic/GOOGL`, (newMes) => {
//       console.log("is subscribing to GOOGLE"+ newMes);
//     });
//     this.stompClient.send("/app/GOOGL",{},(message));
//   }
//
//   TESLA(message: string){
//     this.stompClient.send("/topic/questions",{},(message));
//   }

   displayedColumns: string[] = ['delete', 'symbol', 'name', 'change', 'changeInPercent', 'open', 'prevClose', 'high', 'low', 'volume'];
  //dataSource = new MatTableDataSource<Daily>();
  //bogusDataSource = new MatTableDataSource<Daily>(null);
  dataSource: any;
//  displayedColumns = ['position', 'name', 'weight', 'symbol'];

  bogusDataSource = new MatTableDataSource<Daily>(null);

  constructor(
    private watchlistService: WatchlistService
  ) {
  }
  ngOnInit(): void {
    this.watchlistService.findAllDailiesByWatchlistId(37).subscribe(dailies => {
      this.dataSource = new MatTableDataSource<Daily>(dailies);
    });
  }
}

export interface Element {
  name: string;
  position: number;
  weight: number;
  symbol: string;
}

const ELEMENT_DATA: Element[] = [
  {position: 1, name: 'Hydrogen', weight: 1.0079, symbol: 'H'},
  {position: 2, name: 'Helium', weight: 4.0026, symbol: 'He'},
  {position: 3, name: 'Lithium', weight: 6.941, symbol: 'Li'},
  {position: 4, name: 'Beryllium', weight: 9.0122, symbol: 'Be'},
  {position: 5, name: 'Boron', weight: 10.811, symbol: 'B'},
  {position: 6, name: 'Carbon', weight: 12.0107, symbol: 'C'},
  {position: 7, name: 'Nitrogen', weight: 14.0067, symbol: 'N'},
  {position: 8, name: 'Oxygen', weight: 15.9994, symbol: 'O'},
  {position: 9, name: 'Fluorine', weight: 18.9984, symbol: 'F'},
  {position: 10, name: 'Neon', weight: 20.1797, symbol: 'Ne'},
  {position: 11, name: 'Sodium', weight: 22.9897, symbol: 'Na'},
  {position: 12, name: 'Magnesium', weight: 24.305, symbol: 'Mg'},
  {position: 13, name: 'Aluminum', weight: 26.9815, symbol: 'Al'},
  {position: 14, name: 'Silicon', weight: 28.0855, symbol: 'Si'},
  {position: 15, name: 'Phosphorus', weight: 30.9738, symbol: 'P'},
  {position: 16, name: 'Sulfur', weight: 32.065, symbol: 'S'},
  {position: 17, name: 'Chlorine', weight: 35.453, symbol: 'Cl'},
  {position: 18, name: 'Argon', weight: 39.948, symbol: 'Ar'},
  {position: 19, name: 'Potassium', weight: 39.0983, symbol: 'K'},
  {position: 20, name: 'Calcium', weight: 40.078, symbol: 'Ca'},
];
