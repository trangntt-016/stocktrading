import { Component, Input, OnChanges, OnInit, ViewChild } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { RegisterComponent } from './register/register.component';
import { Router } from '@angular/router';

@Component({
  selector: 'app-auth',
  templateUrl: './auth.component.html',
  styleUrls: ['./auth.component.css']
})
export class AuthComponent implements OnInit {
  error: string;

  ngOnInit(): void {
  }

  handleError(event): void{
    this.error = event;
  }

}
