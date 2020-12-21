import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {MenuController} from "@ionic/angular";

@Component({
    selector: 'app-main',
    templateUrl: './main.page.html',
    styleUrls: ['./main.page.scss'],
})
export class MainPage implements OnInit {

    constructor(private router: Router, private menu: MenuController) {
    }

    ngOnInit() {

    }

    ionViewDidEnter(){

    }

    logout() {
        localStorage.clear();
        this.menu.close()
        this.router.navigate(['/login']);
    }
}
