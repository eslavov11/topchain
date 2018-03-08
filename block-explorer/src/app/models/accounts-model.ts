import {BalanceModel} from "./balance-model";

export class Account {
  address: string;
  confirmedBalance: BalanceModel;
  lastMinedBalance: BalanceModel;
  pendingBalance: BalanceModel;
}
