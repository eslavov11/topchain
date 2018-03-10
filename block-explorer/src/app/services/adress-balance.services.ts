import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import Utils from "../utils/utils";
import {AccountModel} from "../models/accounts-model";
import {AddressTransactions} from "../models/address-transactions";

@Injectable()
export class AddressBalanceService {
  constructor(private http: HttpClient) {
  }

  public getAddressBalance(address: string) {
    return this.http.get<AccountModel>(Utils.NODE_URL + 'address/' + address + '/balance');
  }
  public getAddressTransactions(address: string) {
    return this.http.get<AddressTransactions>(Utils.NODE_URL + 'address/' + address + '/transactions');
  }

}
