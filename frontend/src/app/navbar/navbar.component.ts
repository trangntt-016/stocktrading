import { Component, OnInit } from '@angular/core';
import { DataManagerService } from '../service/data-manager.service';
import { Symbol } from '../model/Symbol';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { SymbolService } from '../service/symbol.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {
  user: any = null;
  symbols$: Observable<Symbol[]>;
  searchSymbol: string;

  constructor(
    private symbolService: SymbolService
  ) { }

  ngOnInit(): void {
    this.symbols$ = this.symbolService.getAllSymbols();
  }

  doFilter(): void{
    this.symbols$ = this.symbols$
      .pipe(map(symbols => this.filter(symbols)),
      );
  }

  filter(values): any{
    return values.filter(symbol => {
      return symbol.symbol.toUpperCase().includes(this.searchSymbol.toUpperCase());
    });
  }

}
