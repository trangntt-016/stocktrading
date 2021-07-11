//  npm i net -S to solve bug cannot resolve net ...
// (window as any).global = window in polyfill.ts to solve global uncaught reference ...

import { Component, OnInit } from '@angular/core';
import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';
import { NgForm } from '@angular/forms';
import { SocialAuthService, SocialUser } from 'angularx-social-login';
import { FacebookLoginProvider, GoogleLoginProvider } from "angularx-social-login";

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

  user: SocialUser;
  loggedIn: boolean;

  constructor(private authService: SocialAuthService) { }

  ngOnInit() {
    this.authService.authState.subscribe((user) => {
      console.log(user);
      this.user = user;
      this.loggedIn = (user != null);
    });
  }

  signInWithGoogle(): void {
    this.authService.signIn(GoogleLoginProvider.PROVIDER_ID);
  }

  signInWithFB(): void {
    this.authService.signIn(FacebookLoginProvider.PROVIDER_ID);
  }

  signOut(): void {
    this.authService.signOut();
  }
}
