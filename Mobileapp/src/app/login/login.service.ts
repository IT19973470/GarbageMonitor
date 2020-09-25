import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {LoginDto} from "./loginDto";
import {Observable} from "rxjs";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class LoginService {

    constructor(private http: HttpClient) {
    }

    accLogin(member:LoginDto): Observable<LoginDto> {
        return this.http.post<LoginDto>(environment.backend_url + URL + "/login", member);
    }
}
