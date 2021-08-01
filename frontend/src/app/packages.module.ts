import { NgModule } from '@angular/core';

import { ShowHidePasswordModule } from 'ngx-show-hide-password';
import { NgApexchartsModule } from 'ng-apexcharts';


@NgModule({
  imports: [
    ShowHidePasswordModule,
    NgApexchartsModule
  ],
  exports: [
    ShowHidePasswordModule,
    NgApexchartsModule,
  ]
})
export class PackagesModule {
}
