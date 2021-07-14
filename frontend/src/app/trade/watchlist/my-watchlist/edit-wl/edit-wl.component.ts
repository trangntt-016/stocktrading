import { Component, Inject, OnDestroy, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialog } from '@angular/material/dialog';
import { WatchlistService } from '../../../../service/watchlist.service';
import { Watchlist } from '../../../../model/Watchlist';
import { AddWlComponent } from '../add-wl/add-wl.component';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-edit-wl',
  templateUrl: './edit-wl.component.html',
  styleUrls: ['./edit-wl.component.css']
})
export class EditWlComponent implements OnInit, OnDestroy {
  myWL: any[] = new Array();
  subscription: Subscription;

  constructor(
    public dialog: MatDialog,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private watchlistService: WatchlistService
  ) {}

  ngOnInit(): void{
    this.data.watchlists.forEach(wl => {
      const wlObj = Object.assign({
        watchlist: wl.name,
        editable: false
      });
      this.myWL.push(wlObj);
    });
  }


  edit(index): void{
    this.myWL[index].editable = true;
  }

  cancel(index){
    this.myWL[index].editable = false;
  }

  update(index): void{
    this.data.watchlists[index].name = this.myWL[index].watchlist;
    this.subscription = this.watchlistService.updateAWatchlist(this.data.watchlists[index]).subscribe(
      () => {
        this.watchlistService.sendWatchlists(this.data.watchlists);
        this.data.watchlists[index].name = this.myWL[index].watchlist;
        this.myWL[index].editable = false;
      },
      (error) => {
        this.myWL[index].editable = false;
        console.log(error);
    });
  }

  delete(index): void{
    this.subscription = this.watchlistService.deleteAWatchlist(this.data.watchlists[index].watchlistId).subscribe(
      () => {
        // update in the edit dialog
        this.myWL = this.myWL.filter(wl => wl.watchlist !== this.data.watchlists[index].name);
        // update in the watchlist component
        this.data.watchlists = this.data.watchlists.filter(wl => wl.watchlistId !== this.data.watchlists[index].watchlistId);
        this.watchlistService.sendWatchlists(this.data.watchlists);
      },
      (error) => {
        this.myWL[index].editable = false;
        console.log(error);
      });
  }

  addWatchlist(): void{
    this.subscription = this.dialog.open(AddWlComponent, {
      data: {
        watchlists: this.data.watchlists
      }
    }).afterClosed().subscribe(result => {
      // receive watchlist object from add dialog and push to the current array
      this.myWL.push(result.data);
    });
  }

  ngOnDestroy():void{
    this.subscription.unsubscribe();
  }

}
