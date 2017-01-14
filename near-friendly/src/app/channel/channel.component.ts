import { Component, OnInit } from '@angular/core';

@Component({
    moduleId: module.id,
    selector: 'near-channel',
    templateUrl: 'channel.component.html'
})
export class ChannelComponent implements OnInit {
    private name: String;

    constructor() {
        this.name = 'Channel component !';
    }

    ngOnInit() { }
}
