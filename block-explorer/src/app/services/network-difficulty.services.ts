import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";

@Injectable()
export class NetworkDifficultyService {
    blocksUrl = 'http://localhost:5555/info';
    constructor(private http:HttpClient) {}

    getNetworkDifficulty(){
        return this.http.get(this.blocksUrl);
    }

}