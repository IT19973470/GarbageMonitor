import {Injectable} from '@angular/core';
import {environment} from "../../../environments/environment";
import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';
import {Subject} from "rxjs";

@Injectable({
    providedIn: 'root'
})
export class RoadmapService {

    socket;
    stompClient;
    binUpdate = new Subject();
    binsSet = new Subject();

    constructor() {
        this.initSocket()
    }

    initSocket() {
        // console.log(12)
        this.socket = new SockJS(environment.backend_url + "/lms-web-socket");
        this.stompClient = Stomp.over(this.socket);
        this.stompClient.debug = null
        let that = this;
        this.stompClient.connect({}, function () {
            that.stompClient.subscribe('/topic/greetings1', function (binStatus) {
                that.binUpdate.next(binStatus.body)
                // console.log(binStatus.body)
            });
            that.stompClient.subscribe('/topic/greetings2', function (binStatus) {
                that.binsSet.next(JSON.parse(binStatus.body))
                // console.log(binStatus.body)
            });
        })
    }

    send() {
        this.stompClient.send("/hello1");
    }
}
