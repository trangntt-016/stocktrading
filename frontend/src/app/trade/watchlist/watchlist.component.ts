import { Component, OnDestroy, OnInit } from '@angular/core';
import {MatAutocompleteModule} from '@angular/material/autocomplete';
import { Watchlist } from '../../model/Watchlist';
import { WatchlistService } from '../../service/watchlist.service';
import { Observable, Subscription } from 'rxjs';
import { SymbolService } from '../../service/symbol.service';
import { Symbol } from '../../model/Symbol';
import { map } from 'rxjs/operators';

@Component({
  selector: 'app-watchlist',
  templateUrl: './watchlist.component.html',
  styleUrls: ['./watchlist.component.css']
})
export class WatchlistComponent implements OnInit, OnDestroy {
  private subscription: Subscription;
  isDisplaySymbols: boolean = false;
  watchlists: Watchlist[];
  selectedWatchlist: Watchlist;
  searchSymbol: string;
  symbols$: Observable<Symbol[]>;

  constructor(
    private watchlistService: WatchlistService,
    private symbolService: SymbolService
  ) { }

  ngOnInit(): void {
    this.subscription = this.watchlistService.getAllWatchlistsByUserId("U_004").subscribe(wl => {
      this.watchlistService.sendWatchlists(wl);
      this.selectedWatchlist = wl[0];
    });


  this.symbols$ = this.symbolService.getAllSymbols();

  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }

  displaySearchSymbol(): void{
    this.isDisplaySymbols = !this.isDisplaySymbols;
  }

  doFilter(): void{
    this.symbols$ = this.symbols$
      .pipe(map(symbols => {
        const filteredSymbols = this.filter(symbols);
        return filteredSymbols;
      }
    ));
  }

  filter(values): any{
    return values.filter(symbol => {
      return symbol.symbol.toUpperCase().includes(this.searchSymbol.toUpperCase());
    });
  }

  handleMyWatchlist(event): void{
    this.selectedWatchlist = event;
  }

  add(symbol: Symbol): void{
    // add new symbol to a watchlist
    this.selectedWatchlist.symbols.push(symbol);
    this.watchlistService.updateAWatchlist(this.selectedWatchlist).subscribe(wl => {
      this.watchlistService.sendWatchlists(wl);
    },(error => {
      console.log(error);
    }));
  }

}
