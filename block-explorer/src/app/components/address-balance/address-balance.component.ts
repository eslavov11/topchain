import {Component, OnDestroy, OnInit} from '@angular/core';
import {Router, ActivatedRoute} from '@angular/router';
import {AddressBalanceService} from '../../services/adress-balance.services';
import {AccountModel} from '../../models/accounts-model';
import {AddressTransactions} from "../../models/address-transactions";


@Component({
  selector: 'app-address-balance',
  templateUrl: './address-balance.component.html',
  styleUrls: ['./address-balance.component.scss']
})
export class AddressBalanceComponent implements OnInit, OnDestroy {
  address: string;
  balance: AccountModel;
  transactions: AddressTransactions;
  private sub: any;

  constructor(private route: ActivatedRoute,
              private addressBalanceService: AddressBalanceService) {
  }

  ngOnInit() {
    this.sub = this.route.params.subscribe(params => {
      this.address = params['address'];

      this.addressBalanceService.getAddressBalance(this.address)
        .subscribe((data: AccountModel) => {
          this.balance = data as AccountModel;
        });

      this.addressBalanceService.getAddressTransactions(this.address)
        .subscribe((data: AddressTransactions) => {
          this.transactions = data as AddressTransactions;
        });
    });
  }

  ngOnDestroy() {
    this.sub.unsubscribe();
  }
}
