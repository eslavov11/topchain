import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {BlocksComponent} from './components/blocks/blocks.component';
import {AccountsComponent} from './components/accounts/accounts.component';
import {PendingTransactionsComponent} from './components/pending-transactions/pending-transactions.component';
import {ConfirmedTransactionsComponent} from './components/confirmed-transactions/confirmed-transactions.component';
import {NetworkDifficultyComponent} from './components/network-difficulty/network-difficulty.component';
import {AddressBalanceComponent} from './components/address-balance/address-balance.component';
import {BlockComponent} from "./components/block/block.component";

const routes: Routes = [
  {path: '', redirectTo: 'blocks', pathMatch: 'full'},
  {path: 'blocks', component: BlocksComponent},
  {path: 'blocks/:index', component: BlockComponent},
  {path: 'accounts', component: AccountsComponent},
  {path: 'pending-transactions', component: PendingTransactionsComponent},
  {path: 'confirmed-transactions', component: ConfirmedTransactionsComponent},
  {path: 'network-info', component: NetworkDifficultyComponent},
  {path: 'address-balance/:address', component: AddressBalanceComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
