import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';

import { AppComponent } from './app.component';
import { BlocksComponent } from './components/blocks/blocks.component';
import { PeersComponent } from './components/peers/peers.component';
import { ConfirmedTransactionsComponent } from './components/confirmed-transactions/confirmed-transactions.component';
import { PendingTransactionsComponent } from './components/pending-transactions/pending-transactions.component';
import { AccountsComponent } from './components/accounts/accounts.component';
import { NetworkDifficultyComponent } from './components/network-difficulty/network-difficulty.component';
import { BlockService } from './components/services/blocks.services';


@NgModule({
  declarations: [
    AppComponent,
    BlocksComponent,
    PeersComponent,
    ConfirmedTransactionsComponent,
    PendingTransactionsComponent,
    AccountsComponent,
    NetworkDifficultyComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule
  ],
  providers: [BlockService],
  bootstrap: [AppComponent]
})
export class AppModule { }
