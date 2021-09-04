import { Component, OnInit } from '@angular/core';
import { SymbolService } from '../service/symbol.service';
import { AuthService } from '../service/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit{
  user: any = null;

  constructor(
    private auth: AuthService,
    private router: Router
  ) { }

  ngOnInit() {
    if(this.auth.readToken() !== null){
      this.user = this.auth.readToken().userId;
    }
  }

  logout(): void{
    this.auth.logout();
  }

}
