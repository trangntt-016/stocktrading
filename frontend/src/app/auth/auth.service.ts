import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { RegisterUser } from '../model/RegisterUser';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient) { }

  register(registerUser: RegisterUser): Observable<any>{
    return this.http.post<any>("http://localhost:3000/api/users", registerUser);
  }
}
