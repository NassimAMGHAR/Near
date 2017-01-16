import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { MaterialModule } from '@angular/material';

import { NearComponent } from './near.component';
import { ChannelComponent } from './channel/channel.component';
import { UserComponent } from './user/user.component';
import { LoginComponent } from './login/login.component';
import { PageNotFoundComponent } from './shared/page-not-found/page-not-found.component';
import { NavComponent } from './shared/nav/nav.component';
import { NearContainerComponent } from './shared/container/container.component';
import { SideNavComponent } from './shared/side-nav/side-nav.component';
import { NearRouterModule } from './router.component';


@NgModule({
  declarations: [
    NearComponent, NavComponent, NearContainerComponent, SideNavComponent,
    ChannelComponent, UserComponent, PageNotFoundComponent, LoginComponent
  ],
  imports: [
    NearRouterModule,
    MaterialModule.forRoot(),
    BrowserModule,
    FormsModule,
    HttpModule
  ],
  providers: [],
  bootstrap: [NearComponent]
})
export class AppModule { }
