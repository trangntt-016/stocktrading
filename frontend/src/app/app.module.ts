import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule } from '@angular/common/http';
import { NgxChartsModule } from '@swimlane/ngx-charts';


import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MaterialModule } from './material.module';
import { PackagesModule } from './packages.module';
import { TestComponent } from './test/test.component';
import { SummaryComponent } from './test/summary/summary.component';
import { ConvertStocksToChartPipe } from './utils/convert-stocks-to-chart.pipe';
import { NavbarComponent } from './navbar/navbar.component';
import { HomeComponent } from './home/home.component';
import { BannerComponent } from './home/banner/banner.component';
import { FeaturesComponent } from './home/features/features.component';
import { MarketComponent } from './market/market.component';
import { TrendingTickersComponent } from './market/trending-tickers/trending-tickers.component';
import { SidebarComponent } from './trade/sidebar/sidebar.component';
import { RegisterComponent } from './auth/register/register.component';
import { AuthComponent } from './auth/auth.component';
import { LoginComponent } from './auth/login/login.component';
import { SocialLoginModule, SocialAuthServiceConfig } from 'angularx-social-login';
import {
  GoogleLoginProvider,
  FacebookLoginProvider
} from 'angularx-social-login';
import { TradeComponent } from './trade/trade.component';

@NgModule({
  declarations: [
    AppComponent,
    TestComponent,
    SummaryComponent,
    ConvertStocksToChartPipe,
    NavbarComponent,
    HomeComponent,
    BannerComponent,
    FeaturesComponent,
    MarketComponent,
    TrendingTickersComponent,
    SidebarComponent,
    RegisterComponent,
    AuthComponent,
    LoginComponent,
    TradeComponent

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
            provider: new FacebookLoginProvider('493350595061491')
          }
        ]
      } as SocialAuthServiceConfig,
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
