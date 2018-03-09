import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import Utils from "../utils/utils";

@Injectable()
export class NetworkDifficultyService {

  constructor(private http: HttpClient) {
  }

  getNetworkDifficulty() {
    return this.http.get(Utils.NODE_URL + 'info');
  }

  getPeers() {
    return this.http.get(Utils.NODE_URL + 'peers');
  }
}
