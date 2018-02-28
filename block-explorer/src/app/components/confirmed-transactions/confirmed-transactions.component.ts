import { Component, OnInit } from '@angular/core';
import { ConfirmedTransactionsService } from '../../services/confirmed-transactions.services';
import { Transaction } from '../../models/transaction-model';

@Component({
  selector: 'app-confirmed-transactions',
  templateUrl: './confirmed-transactions.component.html',
  styleUrls: ['./confirmed-transactions.component.scss']
})
export class ConfirmedTransactionsComponent implements OnInit {
  confirmedTransactions:Array<Transaction>;

  constructor(private confTransactionsService:ConfirmedTransactionsService) { }

  ngOnInit() {
    this.confTransactionsService.getConfirmedTransactions().subscribe(x=>this.confirmedTransactions = x as Array<Transaction>);
  }

}
