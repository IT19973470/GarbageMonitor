import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {MenuController} from "@ionic/angular";
import {environment} from "../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {RoadmapService} from "./roadmap/roadmap.service";

@Component({
    selector: 'app-main',
    templateUrl: './main.page.html',
    styleUrls: ['./main.page.scss'],
})
export class MainPage implements OnInit {

    constructor(private router: Router, private menu: MenuController,private http: HttpClient,private r_service:RoadmapService) {
    }

    ngOnInit() {

    }

    ionViewWillEnter(){
        this.r_service.binInitializer.next();
    }

    logout() {
        localStorage.clear();
        this.menu.close()
        this.router.navigate(['/login']);
    }
}
