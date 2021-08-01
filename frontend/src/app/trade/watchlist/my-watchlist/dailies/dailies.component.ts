import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { WatchlistService } from '../../../../service/watchlist.service';
import { Daily } from '../../../../model/Daily';
import { Watchlist } from '../../../../model/Watchlist';
import { Observable, Subscription } from 'rxjs';
import { MatTableDataSource } from '@angular/material/table';

@Component({
  selector: 'app-dailies',
  templateUrl: './dailies.component.html',
  styleUrls: ['./dailies.component.css']
})
export class DailiesComponent implements OnInit, OnDestroy {
  @Input() watchlists: Watchlist[];
  displayedColumns: string[] = ['delete', 'symbol', 'name', 'change', 'changeInPercent', 'open', 'prevClose', 'high', 'low', 'volume'];
  dataSource$: Observable<Daily[]>;
  bogusDataSource = new MatTableDataSource<Daily>(null);
  private selectedWatchlist: Watchlist;
  private subscription: Subscription;

  constructor(
    private watchlistService: WatchlistService
  ) {  }

  ngOnInit(): void {
    // first initialize, cannot subscribe to a watchlists[] cuz cannot set selected = watchlists[0]
    this.selectedWatchlist = this.watchlists[0];
    this.dataSource$ = this.watchlistService.findAllDailiesByWatchlistId(this.watchlists[0].watchlistId);
    // subscribe to selected Watchlist if there are any changes to it (add new symbol, delete symbol, etc) to render a table
    this.subscription = this.watchlistService.selectedWatchlistEvt.subscribe(selected => {
      this.selectedWatchlist = selected;
      this.dataSource$ = this.watchlistService.findAllDailiesByWatchlistId(selected.watchlistId);
    });
    setInterval(() => {
      this.dataSource$ = this.watchlistService.findAllDailiesByWatchlistId(this.selectedWatchlist.watchlistId);
    }, 30000);
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  deleteSymbol(element: Daily): void {
    this.subscription = this.watchlistService.deleteASymbolFromWatchlistId(this.selectedWatchlist.watchlistId, element.symbol.symbolId).subscribe(wl => {
      // update selected watchlist in the table
      this.watchlistService.sendSelectedWatchlist(wl);
      // find index of selectedWatchlist in watchlist
      const idx = this.watchlists.findIndex(value => value.watchlistId === this.selectedWatchlist.watchlistId);
      // replace old selected watchlist with new one
      this.watchlists[idx] = this.selectedWatchlist;
      // update the watchlists
      this.watchlistService.sendWatchlists(this.watchlists);
    });
  }

  sendADaily(daily: Daily): void {
    this.watchlistService.sendSelectedDaily(daily);
  }

}
