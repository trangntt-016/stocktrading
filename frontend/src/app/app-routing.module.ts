import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { TestComponent } from './test/test.component';
import { HomeComponent } from './home/home.component';
import { MarketComponent } from './market/market.component';
import { AuthComponent } from './auth/auth.component';
import { TradeComponent } from './trade/trade.component';

const routes: Routes = [
  {path: 'test', component: TestComponent},
  {path: 'market', component: MarketComponent},
  {path: 'home', component: HomeComponent},
  {path: 'auth', component: AuthComponent},
  {path: 'trade', component: TradeComponent, children: [
      {path: 'watchlist', component: TradeComponent},
      {path: 'stocks', component: TradeComponent}
    ]
  },
  {path: '', redirectTo: 'home', pathMatch: 'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
