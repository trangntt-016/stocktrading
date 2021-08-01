import { Component, EventEmitter, OnDestroy, OnInit, Output } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { EditWlComponent } from './edit-wl/edit-wl.component';
import { Watchlist } from '../../../model/Watchlist';
import { WatchlistService } from '../../../service/watchlist.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-my-watchlist',
  templateUrl: './my-watchlist.component.html',
  styleUrls: ['./my-watchlist.component.css']
})
export class MyWatchlistComponent implements OnInit, OnDestroy {
  @Output() myWatchlistEvt: EventEmitter<Watchlist> = new EventEmitter<Watchlist>();
  watchlists: Watchlist[];
  clickIdx = 0;
  private subscription: Subscription;

  constructor(
    public dialog: MatDialog,
    private watchlistService: WatchlistService
  ) {
  }

  ngOnInit(): void {
    this.subscription = this.watchlistService.watchlistEvt.subscribe(wl => {
      this.watchlists = wl;
    });
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  openDialog(): void {
    this.dialog.open(EditWlComponent, {
      data: {
        watchlists: this.watchlists
      }
    });
  }

  updateIdxSelectedWL(index): void {
    this.clickIdx = index;
    this.myWatchlistEvt.emit(this.watchlists[index]);
    this.watchlistService.sendSelectedWatchlist(this.watchlists[index]);
  }

}
