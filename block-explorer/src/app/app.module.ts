import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';

import { AppComponent } from './app.component';
import { BlocksComponent } from './components/blocks/blocks.component';
import { PeersComponent } from './components/peers/peers.component';
import { ConfirmedTransactionsComponent } from './components/confirmed-transactions/confirmed-transactions.component';
import { PendingTransactionsComponent } from './components/pending-transactions/pending-transactions.component';
import { AccountsComponent } from './components/accounts/accounts.component';
import { NetworkDifficultyComponent } from './components/network-difficulty/network-difficulty.component';
import { BlockService } from './services/blocks.services';
import { AccountsService } from './services/accounts.services';
import { NetworkDifficultyService } from './services/network-difficulty.services';
import { AddressBalanceComponent } from './components/address-balance/address-balance.component';
import { AddressBalanceService } from './services/adress-balance.services';


@NgModule({
  declarations: [
    AppComponent,
    BlocksComponent,
    PeersComponent,
    ConfirmedTransactionsComponent,
    PendingTransactionsComponent,
    AccountsComponent,
    NetworkDifficultyComponent,
    AddressBalanceComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    AppRoutingModule
  ],
  providers: [
    BlockService,
    AccountsService,
    NetworkDifficultyService,
    AddressBalanceService,
    
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
