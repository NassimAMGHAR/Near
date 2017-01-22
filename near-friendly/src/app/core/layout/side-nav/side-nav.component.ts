import { Component, OnInit, ViewChild, ViewEncapsulation } from '@angular/core';
import { MdSidenav } from '@angular/material';

@Component({
    selector: 'near-side-nav',
    templateUrl: 'side-nav.component.html',
    styleUrls: ['./side-nav.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class SideNavComponent implements OnInit {
    @ViewChild('sideBar') sideBar: MdSidenav;

    constructor() { }

    ngOnInit() { }

    public toggleSideBar() {
        this.sideBar.toggle();
    }
}