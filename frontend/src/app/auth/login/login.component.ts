import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { UserAuthRequestDto } from '../../model/UserAuthRequestDto';
import { NgForm } from '@angular/forms';

import { GoogleLoginProvider, SocialAuthService, SocialUser } from 'angularx-social-login';
import { FacebookLoginProvider } from "angularx-social-login";
import { AuthService } from '../../service/auth.service';
import { Router } from '@angular/router';

declare var FB: any;
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  @Output()errorEvt: EventEmitter<string> = new EventEmitter<string>();
  user: SocialUser;
  loggedIn: boolean;
  authUser: UserAuthRequestDto = {
    email: '',
    password: '',
    authenticationType: ''
  };

  constructor(
    private OAuthService: SocialAuthService,
    private authService: AuthService,
    private router: Router
  ) {  }

  ngOnInit(): void {};

  onSubmit(f: NgForm): void{
    this.authService.login(f.value).subscribe((success) =>
    {
      localStorage.setItem('access_token', success.jwt);
      this.router.navigate(['/trade']);
    })
  }
}
