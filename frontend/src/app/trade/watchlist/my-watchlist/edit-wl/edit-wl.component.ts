import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialog } from '@angular/material/dialog';
import { WatchlistService } from '../../watchlist.service';
import { Watchlist } from '../../../../model/Watchlist';
import { AddWlComponent } from '../add-wl/add-wl.component';

@Component({
  selector: 'app-edit-wl',
  templateUrl: './edit-wl.component.html',
  styleUrls: ['./edit-wl.component.css']
})
export class EditWlComponent implements OnInit {
  myWL: any[] = new Array();

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
    this.watchlistService.updateAWatchlist(this.data.watchlists[index]).subscribe(
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
    this.watchlistService.deleteAWatchlist(this.data.watchlists[index].watchlistId).subscribe(
      () => {
        this.data.watchlists.filter(wl => wl.watchlistId != this.data.watchlists[index].watchlistId);
        this.myWL = this.myWL.filter(wl => wl.watchlist!=this.data.watchlists[index].name);
        this.watchlistService.sendWatchlists(this.data.watchlists);
      },
      (error) => {
        this.myWL[index].editable = false;
        console.log(error);
      });
  }

  addWatchlist(): void{
    this.dialog.open(AddWlComponent, {
      data: {
        watchlists: this.data.watchlists
      }
    });
  }

}
