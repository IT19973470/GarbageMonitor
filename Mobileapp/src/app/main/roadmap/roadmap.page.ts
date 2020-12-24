import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import {RoadmapService} from "./roadmap.service";
// import {PlaceDto} from "./placeDto";
// import * as Stomp from '@stomp/stompjs';
// import {RoadmapService} from "./roadmap.service";
// import {SockJS} from 'sockjs-client';

declare var google;

@Component({
    selector: 'app-roadmap',
    templateUrl: './roadmap.page.html',
    styleUrls: ['./roadmap.page.scss'],
})
export class RoadmapPage implements OnInit {

    map: any;
    polyline: any;
    marker1: any;
    marker2: any;
    // allowHighway: boolean = true;
    // googleMapRoutes: Array<object> = new Array<object>();
    // placeLatLong: Array<number> = new Array<number>();
    // @Output() googleMapRoutesOut: EventEmitter<any> = new EventEmitter();
    places = Array();
    binAvailable = Array();
    totalTrip = 0;

    constructor(
        private http: HttpClient,
        private r_service: RoadmapService
    ) {
        this.r_service.binInitializer.subscribe(()=>{
            this.places = Array();
            this.binAvailable = Array();
            this.ionView();
        })

        this.r_service.binUpdate.subscribe((binStatus) => {
            this.changeBinStatus(binStatus);
        })

        this.r_service.binsSet.subscribe((placeDto) => {
            this.places = Array();
            this.setBins(placeDto);
        })

        this.r_service.binAvailable.subscribe((placeDto) => {
            this.setBinAvailable(placeDto);
        })
    }

    ngOnInit() {

    }

    ionView() {
        this.map = new google.maps.Map(document.getElementById('map'), {
            zoom: 15,
            center: {lat: 6.053519, lng: 80.220978},
            // mapTypeId: 'terrain'
        });

        this.http.get<any>(environment.backend_url + "/api/place/shortestPath").subscribe();
        //     // console.log(placeDto)
        //
        //     // this.places[1].placeFrom.binEmpty = 2;
        //     // this.places[2].placeFrom.binEmpty = 1;
        //     // this.changeRouteOnMap();
        //     // this.mapsAPILoader.load().then(() => {
        //     //
        //     //     }12
        //     // );
        // })
    }

    changeBinStatus(binStatus) {
        for (let i = 0; i < this.places.length; i++) {
            if (this.places[i].placeFrom.label === JSON.parse(binStatus).placeFrom.label) {
                this.places[i].placeFrom.binEmpty = 2;
                if (i < this.places.length - 1) {
                    this.places[i + 1].placeFrom.binEmpty = 1;
                }
            }
        }
    }

    setBins(placeDto) {
        this.totalTrip = placeDto.distance;
        for (let i = 0; i < placeDto['placeDistances'].length; i++) {
            // this.marker1 = new google.maps.Marker({
            //     position: new google.maps.LatLng(placeDtos[i].latitude, placeDtos[i].longitude),
            //     map: this.map,
            //     label: placeDtos[i].label
            // });

            this.places.push(placeDto['placeDistances'][i])
            if (i === placeDto['placeDistances'].length - 1) {
                this.places.push({
                    placeFrom: {
                        label: placeDto['placeDistances'][i].placeTo.label,
                        location: placeDto['placeDistances'][i].placeTo.location,
                        latitude: placeDto['placeDistances'][i].placeTo.latitude,
                        longitude: placeDto['placeDistances'][i].placeTo.longitude,
                        mainLocation: placeDto['placeDistances'][i].placeTo.mainLocation,
                        weight: placeDto['placeDistances'][i].placeTo.weight,
                        binEmpty: placeDto['placeDistances'][i].placeTo.binEmpty
                    },
                    distance: 1.0
                })
            }
        }
        console.log(this.places)
        this.places[0].placeFrom.binEmpty = 1;
    }

    setBinAvailable(placeDto) {
        this.binAvailable.push(placeDto)
    }
}
