import { Component, OnInit } from '@angular/core';
import { RegisterUser } from '../../model/RegisterUser';
import { NgForm } from '@angular/forms';

import { GoogleLoginProvider, SocialAuthService, SocialUser } from 'angularx-social-login';
import { FacebookLoginProvider } from "angularx-social-login";

declare var FB: any;
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  user: SocialUser;
  loggedIn: boolean;
  registerUser: RegisterUser = {
    email: '',
    password: '',
    authenticationType: ''
  };

  constructor(private authService: SocialAuthService) {
  }

  ngOnInit() {
    this.authService.authState.subscribe((user) => {
      console.log(user);
      this.user = user;
      this.loggedIn = (user != null);
    });
  }
  onSubmit(f: NgForm): void{

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
