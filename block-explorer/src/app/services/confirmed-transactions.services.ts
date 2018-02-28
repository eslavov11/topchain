import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";

@Injectable()
export class ConfirmedTransactionsService{
    confirmedTransactionsURL = "http://localhost:5555/transactions/confirmed";
    constructor(private http:HttpClient) {}

    getConfirmedTransactions(){
        return this.http.get(this.confirmedTransactionsURL);
    }
}