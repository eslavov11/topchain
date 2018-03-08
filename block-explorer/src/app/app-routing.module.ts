import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {BlocksComponent} from './components/blocks/blocks.component';
import {AccountsComponent} from './components/accounts/accounts.component';
import {PeersComponent} from './components/peers/peers.component';
import {PendingTransactionsComponent} from './components/pending-transactions/pending-transactions.component';
import {ConfirmedTransactionsComponent} from './components/confirmed-transactions/confirmed-transactions.component';
import {NetworkDifficultyComponent} from './components/network-difficulty/network-difficulty.component';
import {AddressBalanceComponent} from './components/address-balance/address-balance.component';

const routes: Routes = [
  {path: '', redirectTo: 'blocks', pathMatch: 'full'},
  {path: 'blocks', component: BlocksComponent},
  {path: 'accounts', component: AccountsComponent},
  {path: 'peers', component: PeersComponent},
  {path: 'pending-transactions', component: PendingTransactionsComponent},
  {path: 'confirmed-transactions', component: ConfirmedTransactionsComponent},
  {path: 'network-difficulty', component: NetworkDifficultyComponent},
  {path: 'address-balance/:address', component: AddressBalanceComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
