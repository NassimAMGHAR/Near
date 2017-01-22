import { NgModule } from '@angular/core';


import { NearComponent } from './near.component';
// import { NearRouterModule } from './core/router.module';
import { CoreModule } from './core/core.module';


@NgModule({
  imports: [
    CoreModule
  ],
  declarations: [
    NearComponent
  ],
  providers: [],
  bootstrap: [NearComponent]
})
export class AppModule { }
