import { Component, OnInit } from '@angular/core';
import { SymbolService } from '../service/symbol.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent {
  user: any = null;

  constructor() { }

}
