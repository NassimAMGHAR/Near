import { Component, OnInit } from '@angular/core';
import { Channel } from './channel.model';
import { ChannelService } from './channel.service';

@Component({
    selector: 'near-channel',
    templateUrl: './channel.component.html',
    styleUrls: ['./channel.component.scss']
})
export class ChannelComponent implements OnInit {
    private name: String;
    private channels: Channel[];

    constructor(private channelService: ChannelService) {        // TODO : create a service for channels and inject it here 
    }

    ngOnInit() {
        this.name = 'List of accessible channels';
        this.channels = [
            { name: 'News', description: 'This channel objective is to talk about world and general news !' },
            { name: 'Fun', description: 'This channel objective is to talk about funny things ans stuff :)' },
            { name: 'Test', description: 'This channel tests yaay' }
        ];
    }

    refreshChannels() {
        // tests
        this.channelService.loadChannels();
    }

    stopResfesh(){
        //tests
        this.channelService.stop();
    }
}
