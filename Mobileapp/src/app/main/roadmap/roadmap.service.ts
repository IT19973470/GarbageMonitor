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
                console.log(binStatus.body)
            });
        })
        // if (this.stompClient != undefined) {
        //     if (this.stompClient.connected) {
        //         this.stompClient.disconnect()
        //     }
        // }
        // this.socket = new SockJS("http://192.168.1.4:8080/gs-guide-websocket")
        // console.log(this.socket)
        //
        // this.stompClient = new Stomp.Client({
        //     brokerURL: 'ws://192.168.1.4:8080/gs-guide-websocket',
        //
        //     debug: function (str) {
        //         console.log(str);
        //     },
        //     reconnectDelay: 5000,
        //     // heartbeatIncoming: 4000,
        //     // heartbeatOutgoing: 4000
        // });
        //
        //
        // // this.stompClient = Stomp.over(this.socket);
        // // this.stompClient.reconnect_delay=1000
        // let that = this;
        //
        // this.stompClient.connect({}, function () {
        //         console.log('Network connected')
        //         that.times.push('Network connected')
        //         that.cTimes.next(that.times)
        //         that.stompClient.subscribe('/topic/greetings', function (user) {
        //             that.times.push(user.body)
        //             that.cTimes.next(that.times)
        //         });
        //         that.stompClient.send("/app/hello");
        //     }
        //     , function (err) {
        //         console.log('Socket is closed. Reconnect will be attempted in 5 seconds (' + ++that.i + ').');
        //         that.times.push('Connection Lost' + that.i)
        //         that.cTimes.next(that.times)
        //         // setTimeout(function () {
        //         //     that.connect2();
        //         // }, 5000);
        //     }
        // );

        // console.log(this.stompClient)
        // this.stompClient.debug = null
        // this.stompClient.reconnect_delay = 5000;


        // this.stompClient.onConnect = function(frame) {
        //     console.log(frame)
        // };
        //
        // this.stompClient.activate();
        // this.connectSocket()
        // this.connect2()

        // let that = this;
        // this.webSoc = new WebSocket('ws://192.168.1.4:8080/socket');
        // this.webSoc.onopen = function (ev) {
        //     // that.i = 0;
        //     console.log('Network connected')
        //     that.webSoc.send(JSON.stringify(0));
        //     // that.times.push('Network connected')
        //     // that.cTimes.next(that.times)
        // }
        //
        // this.webSoc.onmessage = function (ev) {
        //     console.log(ev.data);
        //     // that.times.push(ev.data)
        //     // that.cTimes.next(that.times)
        // }
        //
        // this.webSoc.onclose = function (ev) {
        //     // console.log('Socket is closed. Reconnect will be attempted in 5 seconds (' + ++that.i + ').', ev.reason);
        //     // that.times.push('Connection Lost' + that.i)
        //     // that.cTimes.next(that.times)
        //     setTimeout(function () {
        //         // that.connectSocket();
        //     }, 5000);
        // }
        //
        // this.webSoc.onerror = function (err) {
        //     console.error('Socket encountered error: ', err.message, 'Closing socket');
        //     // that.times.push('Socket encountered error')
        //     // that.cTimes.next(that.times)
        // };
    }

    public send(): void {
        this.stompClient.send("/hello1");
    }
}
