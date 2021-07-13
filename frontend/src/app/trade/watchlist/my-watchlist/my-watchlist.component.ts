import { Component, Input, OnInit } from '@angular/core';
import {MatDialog} from '@angular/material/dialog';
import { EditWlComponent } from './edit-wl/edit-wl.component';
import { Watchlist } from '../../../model/Watchlist';
import { WatchlistService } from '../watchlist.service';

@Component({
  selector: 'app-my-watchlist',
  templateUrl: './my-watchlist.component.html',
  styleUrls: ['./my-watchlist.component.css']
})
export class MyWatchlistComponent implements OnInit {
  watchlists: Watchlist[];
  clickIdx: number = 0;
  constructor(
    public dialog: MatDialog,
    private watchlistService: WatchlistService
  ) { }

  ngOnInit(): void {
    this.watchlistService.watchlistEvt.subscribe(wl => {
      this.watchlists = wl;
      this.dialog.closeAll();
    });
  }

  openDialog(): void{
    this.dialog.open(EditWlComponent,{
      data: {
        watchlists: this.watchlists
      }
    });
  }

  updClick(index): void{
    this.clickIdx = index;
    console.log(this.clickIdx);
  }

}

