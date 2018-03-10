import {BalanceModel} from "./balance-model";

export class AccountModel {
  address: string;
  confirmedBalance: BalanceModel;
  lastMinedBalance: BalanceModel;
  pendingBalance: BalanceModel;
}
