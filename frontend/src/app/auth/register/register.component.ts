import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { NgForm } from '@angular/forms';
import { UserAuthRequestDto } from '../../model/UserAuthRequestDto';
import { AuthService } from '../../service/auth.service';
import { Router } from '@angular/router';
import { FacebookLoginProvider, GoogleLoginProvider, SocialAuthService } from 'angularx-social-login';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  @Output()errorEvt: EventEmitter<string> = new EventEmitter<string>();
  authUser: UserAuthRequestDto = {
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
      this.authUser.email = user.email;
      this.authUser.authenticationType = user.provider;
      this.authService.register(this.authUser).subscribe(
        (newUsr) => {
          localStorage.setItem('access_token', newUsr.jwt);
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
      this.authUser.email = f.value.email;
      this.authUser.password = f.value.password;
      this.authUser.authenticationType = "DATABASE";

      this.authService.register(this.authUser).subscribe(
        (newUsr) => {
          localStorage.setItem("access_token", newUsr.jwt);
          this.router.navigate(['/trade']);
        }
        ,(err) => {
          this.errorEvt.emit(err.error);
        })
    }
  }

  signInWithFb(): void {
    this.OAuthService.signIn(FacebookLoginProvider.PROVIDER_ID);
  }

  signInWithGoogle(): void {
    this.OAuthService.signIn(GoogleLoginProvider.PROVIDER_ID);
  }

}
