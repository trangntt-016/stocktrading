import { Component, Input, OnChanges, OnInit, ViewChild } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { RegisterComponent } from './register/register.component';

@Component({
  selector: 'app-auth',
  templateUrl: './auth.component.html',
  styleUrls: ['./auth.component.css']
})
export class AuthComponent implements OnInit {
  error: string;

  ngOnInit(): void {
  }

  handleError(event:any){
    this.error = event;
  }


}
