import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import Utils from "../utils/utils";

@Injectable()
export class PendingTransactionsService {
  pendingTransactionsURL = Utils.NODE_URL + 'transactions/pending';

  constructor(private http: HttpClient) {
  }

  getPendingTransactions() {
    return this.http.get(this.pendingTransactionsURL);
  }
}
