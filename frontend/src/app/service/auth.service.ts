import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { UserAuthRequestDto } from '../model/UserAuthRequestDto';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient) { }

  register(registerUser: UserAuthRequestDto): Observable<any>{
    console.log("register")
    return this.http.post<any>(`${environment.userAPI}/register`, registerUser).pipe(map(res => res.data));
  }

  login(registerUser: UserAuthRequestDto): Observable<any> {
    console.log("login")
    return this.http.post<any>(`${environment.userAPI}/login`, registerUser);
  }
}
