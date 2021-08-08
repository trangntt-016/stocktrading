import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule } from '@angular/common/http';
import { NgxChartsModule } from '@swimlane/ngx-charts';


import { AppRoutingModule } from './app-routing.module';
import { ConvertStocksToChartPipe } from './utils/convert-stocks-to-chart.pipe';
import { FormatVolumePipe } from './utils/format-volume.pipe';
import { FormatDatePipe } from './utils/format-date.pipe';
import { AppComponent } from './app.component';
import { MaterialModule } from './material.module';
import { PackagesModule } from './packages.module';
import { NavbarComponent } from './navbar/navbar.component';
import { HomeComponent } from './home/home.component';
import { BannerComponent } from './home/banner/banner.component';
import { FeaturesComponent } from './home/features/features.component';
import { SidebarComponent } from './trade/sidebar/sidebar.component';
import { RegisterComponent } from './auth/register/register.component';
import { AuthComponent } from './auth/auth.component';
import { LoginComponent } from './auth/login/login.component';
import { FacebookLoginProvider, GoogleLoginProvider, SocialAuthServiceConfig, SocialLoginModule } from 'angularx-social-login';
import { TradeComponent } from './trade/trade.component';
import { WatchlistComponent } from './trade/watchlist/watchlist.component';
import { MyWatchlistComponent } from './trade/watchlist/my-watchlist/my-watchlist.component';
import { EditWlComponent } from './trade/watchlist/my-watchlist/edit-wl/edit-wl.component';
import { AddWlComponent } from './trade/watchlist/my-watchlist/add-wl/add-wl.component';
import { DailiesComponent } from './trade/watchlist/my-watchlist/dailies/dailies.component';
import { NewsComponent } from './trade/watchlist/news/news.component';
import { KeyStatsComponent } from './trade/watchlist/key-stats/key-stats.component';
import { PaperComponent } from './trade/paper/paper.component';
import { AccountComponent } from './trade/paper/account/account.component';
import { PaperWatchlistComponent } from './trade/paper/paper-watchlist/paper-watchlist.component';
import { BuysellComponent } from './trade/paper/buysell/buysell.component';
import { ChartComponent } from './trade/paper/chart/chart.component';
import { OrderComponent } from './trade/paper/order/order.component';
import { PositionComponent } from './trade/paper/position/position.component';


@NgModule({
  declarations: [
    AppComponent,
    ConvertStocksToChartPipe,
    NavbarComponent,
    HomeComponent,
    BannerComponent,
    FeaturesComponent,
    SidebarComponent,
    RegisterComponent,
    AuthComponent,
    LoginComponent,
    TradeComponent,
    WatchlistComponent,
    MyWatchlistComponent,
    EditWlComponent,
    AddWlComponent,
    DailiesComponent,
    FormatVolumePipe,
    NewsComponent,
    KeyStatsComponent,
    FormatDatePipe,
    PaperComponent,
    AccountComponent,
    PaperWatchlistComponent,
    BuysellComponent,
    ChartComponent,
    OrderComponent,
    PositionComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    HttpClientModule,
    NgxChartsModule,
    MaterialModule,
    PackagesModule,
    SocialLoginModule

  ],
  providers: [
    {
      provide: 'SocialAuthServiceConfig',
      useValue: {
        autoLogin: false,
        providers: [
          {
            id: GoogleLoginProvider.PROVIDER_ID,
            provider: new GoogleLoginProvider(
              '198122083672-2mv5u10qil27mb5ttl6rt4faa4kst5i3.apps.googleusercontent.com'
            )
          },
          {
            id: FacebookLoginProvider.PROVIDER_ID,
            provider: new FacebookLoginProvider('367021911452824')
          }
        ]
      } as SocialAuthServiceConfig,
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
