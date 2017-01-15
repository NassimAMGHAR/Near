import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { RouterModule, Routes } from '@angular/router';
import { MaterialModule } from '@angular/material';

import { NearComponent } from './near.component';
import { ChannelComponent } from './channel/channel.component';
import { UserComponent } from './user/user.component';
import { PageNotFoundComponent } from './shared/page-not-found/page-not-found.component';
import { NavComponent } from './shared/nav/nav.component';
import { SideNavComponent } from './shared/side-nav/side-nav.component';

const nearRoutes: Routes = [
  {
    path: 'channel',
    component: ChannelComponent,
    data: { title: 'Channel List' }
  }, {
    path: 'user',
    component: UserComponent,
    data: { title: 'User List' }
  },
  {
    path: '',
    redirectTo: '/channel',
    pathMatch: 'full'
  },
  { path: '**', component: PageNotFoundComponent }
];

@NgModule({
  declarations: [
    NearComponent, NavComponent, SideNavComponent, ChannelComponent, UserComponent, PageNotFoundComponent
  ],
  imports: [
    RouterModule.forRoot(nearRoutes),
    MaterialModule.forRoot(),
    BrowserModule,
    FormsModule,
    HttpModule
  ],
  providers: [],
  bootstrap: [NearComponent]
})
export class AppModule { }
