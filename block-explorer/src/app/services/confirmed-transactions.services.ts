import {HttpClient} from "@angular/common/http";
import {Injectable} from "@angular/core";
import Utils from "../utils/utils";

@Injectable()
export class ConfirmedTransactionsService {
  confirmedTransactionsURL = Utils.NODE_URL + "transactions/confirmed";

  constructor(private http: HttpClient) {
  }

  getConfirmedTransactions() {
    return this.http.get(this.confirmedTransactionsURL);
  }
}
