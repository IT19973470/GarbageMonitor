import { Directive } from '@angular/core';
import {AbstractControl, NG_VALIDATORS, Validator} from "@angular/forms";

@Directive({
    selector: '[appEmailOrNicValidator]',
    providers: [{provide: NG_VALIDATORS, useExisting: EmailOrNicValidatorDirective, multi: true}]
})
export class EmailOrNicValidatorDirective implements Validator {

    validate(control: AbstractControl): { [key: string]: any } | null {
        let NIC_REGEX = /^[0-9]{9}[Vv]$/; // Regular Expression 1
        let EMAIL_REGEX = /^[a-z]([a-z0-9]+.)*[a-z0-9]+@[a-z0-9]+.[a-z0-9]+$/; // Regular Expression 2

        if (control.value != undefined) {
            if (control.value.length == 0 || (NIC_REGEX.test(control.value) || EMAIL_REGEX.test(control.value))) {
                return null;
            }
        }

        return {'emailOrNicInvalid': true};
    }

    constructor() {
    }

}
