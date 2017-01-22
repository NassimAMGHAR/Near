import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';

import { CONFIG } from '../config';
import { SpinnerService } from '../spinner/spinner.service';

let channelUrl = CONFIG.baseUrls.channel;

@Injectable()
export class ChannelService {

    constructor(private spinnerService: SpinnerService) { }

    loadChannels(){
        this.spinnerService.show();
    }

    stop(){
        this.spinnerService.hide();
    }
}