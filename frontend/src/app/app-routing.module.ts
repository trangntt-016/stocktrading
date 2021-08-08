import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { AuthComponent } from './auth/auth.component';
import { TradeComponent } from './trade/trade.component';
import { WatchlistComponent } from './trade/watchlist/watchlist.component';
import { PaperComponent } from './trade/paper/paper.component';
import { GuardAuthService } from './service/guard-auth.service';

const routes: Routes = [
  {path: 'home', component: HomeComponent},
  {path: 'auth', component: AuthComponent},
  {
    path: 'trade', component: TradeComponent, canActivate: [GuardAuthService], children: [
      {path: 'watchlist', component: WatchlistComponent},
      {path: 'paper', component: PaperComponent},
      {path: '', redirectTo: 'paper', pathMatch: 'full'}
    ]
  },
  {path: '**', redirectTo: 'home', pathMatch: 'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
