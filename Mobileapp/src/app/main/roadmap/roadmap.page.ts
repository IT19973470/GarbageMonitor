import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import {PlaceDto} from "./placeDto";

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

    constructor(
        private http: HttpClient
    ) {
    }

    ngOnInit() {

    }

    ionViewWillEnter() {
        this.map = new google.maps.Map(document.getElementById('map'), {
            zoom: 15,
            center: {lat: 6.053519, lng: 80.220978},
            // mapTypeId: 'terrain'
        });

        this.http.get<Array<PlaceDto>>(environment.backend_url + "/api/place/getPlaces").subscribe((placeDtos) => {
            for (let i = 0; i < placeDtos.length; i++) {
                this.marker1 = new google.maps.Marker({
                    position: new google.maps.LatLng(placeDtos[i].latitude, placeDtos[i].longitude),
                    map: this.map,
                    label: placeDtos[i].label
                });
                this.places.push(placeDtos[i])
            }
            // this.changeRouteOnMap();
            // this.mapsAPILoader.load().then(() => {
            //
            //     }
            // );
        })
    }

    // changeRouteOnMap(mapRoute) {
        // const infowindow = new google.maps.InfoWindow();

        // let origin = new google.maps.LatLng(mapRoute[0], mapRoute[1]);
        // let destination = new google.maps.LatLng(mapRoute[2], mapRoute[3]);
        // if (this.polyline != undefined && this.marker1 != undefined && this.marker2 != undefined) {
        //     this.polyline.setMap(null);
        //     this.marker1.setMap(null);
        //     this.marker2.setMap(null);
        // }

        // if (mapRoute != null) {
        // this.polyline = new google.maps.Polyline({
        //     path: new google.maps.geometry.encoding.decodePath(mapRoute[4]),
        //     map: this.map,
        //     strokeColor: '#4872ff',
        //     strokeWeight: 5,
        //     strokeOpacity: 0.7,
        // });

        // this.marker1 = new google.maps.Marker({
        //     position: origin,
        //     map: this.map,
        //     title: 'Hello World!',
        //     label: 'A'
        // });
        //
        // this.marker2 = new google.maps.Marker({
        //     position: destination,
        //     map: this.map,
        //     title: 'Hello World!'
        // });
    // }

    // }
}
