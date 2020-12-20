import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';

import {IonicModule} from '@ionic/angular';

import {LoginPageRoutingModule} from './login-routing.module';

import {LoginPage} from './login.page';
import {EmailOrNicValidatorDirective} from "./email-or-nic-validator.directive";

@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        IonicModule,
        LoginPageRoutingModule
    ],
    declarations: [LoginPage, EmailOrNicValidatorDirective]
})
export class LoginPageModule {
}
