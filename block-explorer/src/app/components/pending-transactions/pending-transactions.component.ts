import { Component, OnInit } from '@angular/core';
import { PendingTransactionsService } from '../../services/pending-transactions.services';
import { Transaction } from '../../models/transaction-model';

@Component({
  selector: 'app-pending-transactions',
  templateUrl: './pending-transactions.component.html',
  styleUrls: ['./pending-transactions.component.scss']
})
export class PendingTransactionsComponent implements OnInit {
  pendingTransactions:Array<Transaction>;
  constructor(private pendingTransactionsService:PendingTransactionsService) { }

  ngOnInit() {

    this.pendingTransactionsService.getPendingTransactions().subscribe(x=>{
      this.pendingTransactions = x as Array<Transaction>;
    });
  }

}
