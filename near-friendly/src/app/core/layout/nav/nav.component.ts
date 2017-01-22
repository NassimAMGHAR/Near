import { Component, OnInit, Output, EventEmitter } from '@angular/core';

@Component({
    selector: 'near-nav',
    templateUrl: './nav.component.html',
    styleUrls: ['./nav.component.scss']
})
export class NavComponent implements OnInit {

    @Output() menuClicked: EventEmitter<any>;
    constructor() {
        this.menuClicked = new EventEmitter();
    }

    ngOnInit() { }

    onMenuClicked() {
        this.menuClicked.emit({});
    }
}