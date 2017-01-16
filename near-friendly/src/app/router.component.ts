import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { NearComponent } from './near.component';
import { ChannelComponent } from './channel/channel.component';
import { UserComponent } from './user/user.component';
import { LoginComponent } from './login/login.component';
import { PageNotFoundComponent } from './shared/page-not-found/page-not-found.component';
import { NearContainerComponent } from './shared/container/container.component';
import { SideNavComponent } from './shared/side-nav/side-nav.component';

const nearRoutes: Routes = [
    {
        path: 'login',
        component: LoginComponent,
        data: { title: 'login page' }

    }, {
        path: '',
        component: NearContainerComponent,
        children: [
            {
                path: 'channel',
                component: ChannelComponent,
                data: { title: 'Channel List' }
            }, {
                path: 'user',
                component: UserComponent,
                data: { title: 'User List' }
            }
        ]
    },
    { path: '**', component: PageNotFoundComponent }
];

@NgModule({
    imports: [RouterModule.forRoot(nearRoutes)],
    exports: [RouterModule],
})
export class NearRouterModule { }
