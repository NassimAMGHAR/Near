import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';

import { NearComponent } from './near.component';
import { ChannelComponent } from './channel/channel.component';

@NgModule({
  declarations: [
    NearComponent, ChannelComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule
  ],
  providers: [],
  bootstrap: [NearComponent]
})
export class AppModule { }
