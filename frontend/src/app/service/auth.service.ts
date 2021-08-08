import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { UserAuthRequestDto } from '../model/UserAuthRequestDto';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { map } from 'rxjs/operators';
import { JwtHelperService } from '@auth0/angular-jwt';


import {Payload} from '../model/Payload'
import { Router } from '@angular/router';
import { SocialAuthService } from 'angularx-social-login';
import { GoogleLoginProvider } from 'angularx-social-login';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(
    private http: HttpClient,
    private router: Router,
    private OAuthService: SocialAuthService
  ) { }

  private helper = new JwtHelperService();

  register(registerUser: UserAuthRequestDto): Observable<any>{
    return this.http.post<any>(`${environment.userAPI}/register`, registerUser).pipe(map(res => res.data));
  }

  login(registerUser: UserAuthRequestDto): Observable<any> {
    return this.http.post<any>(`${environment.userAPI}/login`, registerUser).pipe(map(res => res.data));
  }

  getToken(): string{
    if (localStorage.getItem('access_token')){
      return localStorage.getItem('access_token');
    }
  }

  readToken(): Payload{
    const rawToken = this.getToken();
    return this.helper.decodeToken(rawToken);
  }

  isAuthenticated(): boolean{
    if (this.getToken()){
      return true;
    }
    return false;
  }

  public logout(): void{
    localStorage.removeItem('access_token');
    localStorage.clear();
    this.OAuthService.signOut();

    this.router.navigate(['/']);
  }
}
