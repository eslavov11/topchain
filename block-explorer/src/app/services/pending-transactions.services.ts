import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';

@Injectable()
export class PendingTransactionsService {
  pendingTransactionsURL = 'http://localhost:5555/transactions/pending';

  constructor(private http: HttpClient) {
  }

  getPendingTransactions() {
    return this.http.get(this.pendingTransactionsURL);
  }
}
