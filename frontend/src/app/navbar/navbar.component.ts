import { Component, OnInit } from '@angular/core';
import { DataManagerService } from '../service/data-manager.service';
import { SymbolDto } from '../model/SymbolDto';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {
  user: any = null;
  symbols$: Observable<SymbolDto[]>;
  searchSymbol: string;

  constructor(
    private dataService: DataManagerService
  ) { }

  ngOnInit(): void {
    //this.symbols$ = this.dataService.getAllSymbols();
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
