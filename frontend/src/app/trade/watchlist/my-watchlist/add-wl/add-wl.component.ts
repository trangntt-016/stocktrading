import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { WatchlistService } from '../../watchlist.service';

@Component({
  selector: 'app-add-wl',
  templateUrl: './add-wl.component.html',
  styleUrls: ['./add-wl.component.css']
})
export class AddWlComponent implements OnInit {
  newName: string = "";
  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    private dialogRef: MatDialogRef<AddWlComponent>,
    private watchlistService: WatchlistService
  ) { }

  ngOnInit(): void {
  }

  cancel(): void{
    this.dialogRef.close();
  }

  save(): void{
    this.watchlistService.createAWatchlist("U_004", this.newName).subscribe((wl) => {
      this.data.watchlists.push(wl);
      this.watchlistService.sendWatchlists(this.data.watchlists);
      this.dialogRef.close();
    }, (err) => {
      console.log(err);
    });
  }

}
