import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";

@Injectable()
export class AddressBalanceService{
    constructor(private http:HttpClient) {}
}