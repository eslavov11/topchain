import { Component } from '@angular/core';
import { Http,Headers } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map';


@Component({
    selector: 'home',
    templateUrl: './home.component.html'
})
export class HomeComponent {
    sendCoinssURL:string = 'http://localhost:50501/api/Home/ReturnOnSuccess';
    addressToReceive:string = "";
    txHash:string = "";
    constructor(private http:Http) {}

    receiveResponse(address:string){
        let headers = new Headers({ 'Content-Type': 'application/json' });
        let body = JSON.stringify(address);
        let responseBody = this.http.post(this.sendCoinssURL, body, { headers: headers })
            .subscribe((res: any) => { this.txHash = res.text() });
        return responseBody;
    }
}
