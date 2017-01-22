import { BrowserModule } from '@angular/platform-browser';
import { NgModule, Optional, SkipSelf } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { MaterialModule } from '@angular/material';
import { NearRouterModule } from './router.module';

import { UserComponent } from './user/user.component';
import { NavComponent } from './layout/nav/nav.component';
import { SideNavComponent } from './layout/side-nav/side-nav.component';
import { SpinnerModule } from './spinner/spinner.module';
import { ChannelComponent } from './channel/channel.component';
import { ChannelService } from './channel/channel.service';
import { NearContainerComponent } from './container/container.component';

import { PageNotFoundComponent } from './shared/page-not-found/page-not-found.component';
import { LoginComponent } from './login/login.component';

@NgModule({
    imports: [
        BrowserModule,
        FormsModule,
        HttpModule,
        MaterialModule.forRoot(),
        SpinnerModule,
        NearRouterModule
    ],
    exports: [
        MaterialModule, SpinnerModule, NearRouterModule
    ],
    declarations: [NavComponent, SideNavComponent,
        ChannelComponent, UserComponent, NearContainerComponent,
        LoginComponent, PageNotFoundComponent],
    providers: [ ChannelService ],
})
export class CoreModule {
    constructor( @Optional() @SkipSelf() parentModule: CoreModule) {
        // throwIfAlreadyLoaded(parentModule, 'CoreModule');
    }
}
