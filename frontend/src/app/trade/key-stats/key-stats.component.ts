import { Component, OnInit } from '@angular/core';
import { WatchlistService } from '../../service/watchlist.service';
import { Daily } from '../../model/Daily';

@Component({
  selector: 'app-key-stats',
  templateUrl: './key-stats.component.html',
  styleUrls: ['./key-stats.component.css']
})
export class KeyStatsComponent implements OnInit {
  stock: Daily;
  constructor(
    private watchlistService: WatchlistService
  ) { }

  ngOnInit(): void {
    // subscribe to any changes in the selected watchlist
    this.watchlistService.selectedWatchlistEvt.subscribe(selected => {
      // find all dailies by that watchlist id
      this.watchlistService.findAllDailiesByWatchlistId(selected.watchlistId).subscribe(dailies=>{
        this.stock = dailies[0];
      });
    });
  }

}
