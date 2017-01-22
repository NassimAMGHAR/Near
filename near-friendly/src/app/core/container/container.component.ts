import { Component, OnInit, ViewChild } from '@angular/core';
import { SideNavComponent } from '../layout/side-nav/side-nav.component';

@Component({
    moduleId: module.id,
    selector: 'near-container',
    templateUrl: 'container.component.html',
    styleUrls: ['./container.component.scss']
})
export class NearContainerComponent implements OnInit {

    @ViewChild(SideNavComponent) sideNavComponent: SideNavComponent;

    constructor() { }

    ngOnInit() { }

    onMenuClicked(event: any) {
        this.sideNavComponent.toggleSideBar();
    }
}