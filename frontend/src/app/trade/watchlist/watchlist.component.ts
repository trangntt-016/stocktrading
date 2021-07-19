import { Component, OnDestroy, OnInit } from '@angular/core';
import {MatAutocompleteModule} from '@angular/material/autocomplete';
import { Watchlist } from '../../model/Watchlist';
import { WatchlistService } from '../../service/watchlist.service';
import { Observable, Subscription } from 'rxjs';
import { SymbolService } from '../../service/symbol.service';
import { Symbol } from '../../model/Symbol';
import { map } from 'rxjs/operators';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-watchlist',
  templateUrl: './watchlist.component.html',
  styleUrls: ['./watchlist.component.css']
})
export class WatchlistComponent implements OnInit, OnDestroy {
  private subscription: Subscription;
  isDisplaySymbols: boolean = false;
  watchlists: Watchlist[] = new Array();
  selectedWatchlist: Watchlist;
  searchSymbol: string;
  symbols$: Observable<Symbol[]>;

  constructor(
    private watchlistService: WatchlistService,
    private symbolService: SymbolService
  ) { }

  ngOnInit(): void {
    this.subscription = this.watchlistService.getAllWatchlistsByUserId("U_004").subscribe(wl => {
      this.watchlists = wl;
      this.watchlistService.sendWatchlists(this.watchlists);
      this.watchlistService.sendSelectedWatchlist(this.watchlists[0]);
      this.selectedWatchlist = this.watchlists[0];
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
    if(this.selectedWatchlist.symbols.length === 0){
      this.selectedWatchlist.symbols = new Array();
    }
    this.selectedWatchlist.symbols.push(symbol);
    // find index of selectedWatchlist in watchlists
    const idx = this.watchlists.findIndex(value => value.watchlistId === this.selectedWatchlist.watchlistId);
    // replace old selected watchlist with new one
    this.watchlists[idx] = this.selectedWatchlist;
    // update selected watchlist with new symbol in the database
    this.watchlistService.updateAWatchlist(this.selectedWatchlist).subscribe(wl => {
      // update successfully will return null
      this.watchlistService.sendWatchlists(this.watchlists);
      this.watchlistService.sendSelectedWatchlist(this.selectedWatchlist);
    }, (error => {
      console.log(error);
    }));


  }

}
