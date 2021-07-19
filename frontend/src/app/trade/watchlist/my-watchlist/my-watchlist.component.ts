import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import {MatDialog} from '@angular/material/dialog';
import { EditWlComponent } from './edit-wl/edit-wl.component';
import { Watchlist } from '../../../model/Watchlist';
import { WatchlistService } from '../../../service/watchlist.service';

@Component({
  selector: 'app-my-watchlist',
  templateUrl: './my-watchlist.component.html',
  styleUrls: ['./my-watchlist.component.css']
})
export class MyWatchlistComponent implements OnInit {
  @Output()myWatchlistEvt: EventEmitter<Watchlist> = new EventEmitter<Watchlist>();
  watchlists: Watchlist[];
  clickIdx: number = 0;
  constructor(
    public dialog: MatDialog,
    private watchlistService: WatchlistService
  ) { }

  ngOnInit(): void {
    this.watchlistService.watchlistEvt.subscribe(wl => {
      this.watchlists = wl;
    });
  }

  openDialog(): void{
    this.dialog.open(EditWlComponent,{
      data: {
        watchlists: this.watchlists
      }
    });
  }

  updateIdxSelectedWL(index): void{
    this.clickIdx = index;
    this.myWatchlistEvt.emit(this.watchlists[index]);
    this.watchlistService.sendSelectedWatchlist(this.watchlists[index]);
  }

}
