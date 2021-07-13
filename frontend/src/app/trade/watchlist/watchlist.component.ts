import { Component, OnInit } from '@angular/core';
import { Watchlist } from '../../model/Watchlist';
import { WatchlistService } from './watchlist.service';

@Component({
  selector: 'app-watchlist',
  templateUrl: './watchlist.component.html',
  styleUrls: ['./watchlist.component.css']
})
export class WatchlistComponent implements OnInit {
  watchlists: Watchlist[];
  constructor(
    private watchlistService: WatchlistService
  ) { }

  ngOnInit(): void {
    this.watchlistService.getAllWatchlistsByUserId("U_004").subscribe(wl => {
      this.watchlistService.sendWatchlists(wl);
    });
  }

}
