import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { NgForm } from '@angular/forms';
import { RegisterUser } from '../../model/RegisterUser';
import { AuthService } from '../auth.service';
import { Router } from '@angular/router';
import { FacebookLoginProvider, GoogleLoginProvider, SocialAuthService } from 'angularx-social-login';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  @Output()errorEvt: EventEmitter<string> = new EventEmitter<string>();
  registerUser: RegisterUser = {
    email: '',
    password: '',
    authenticationType: ''
  };

  constructor(
    private authService: AuthService,
    private OAuthService: SocialAuthService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.OAuthService.authState.subscribe((user) => {
      this.registerUser.email = user.email;
      this.registerUser.authenticationType = user.provider;
      this.authService.register(this.registerUser).subscribe(
        (newUsr) => {
          localStorage.setItem("userId",newUsr.userId);
          this.router.navigate(['/trade']);
        }
        ,(err) => {
          this.errorEvt.emit(err.error);
        })
    },(error)=>{
      this.errorEvt.emit(error.error);
    });
  }

  onSubmit(f: NgForm): void{
    if(f.value.email.length >= 5 && f.value.email.length <= 30 && f.value.password.length >= 8 && f.value.password.length <= 30){
      this.registerUser.email = f.value.email;
      this.registerUser.password = f.value.password;
      this.registerUser.authenticationType = "DATABASE";

      this.authService.register(this.registerUser).subscribe(
        (newUsr) => {
          localStorage.setItem("userId",newUsr.userId);
          this.router.navigate(['/trade']);
        }
        ,(err) => {
          this.errorEvt.emit(err.error);
        })
    }
  }

  signUpWithFb(): void {
    this.OAuthService.signIn(FacebookLoginProvider.PROVIDER_ID);
  }

  signUpWithGoogle(): void {
    this.OAuthService.signIn(GoogleLoginProvider.PROVIDER_ID);
  }

}
