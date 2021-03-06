import {Component, OnInit} from '@angular/core';
import {NetworkDifficultyService} from '../../services/network-difficulty.services';
import {TopchainInfo} from '../../models/Topchain-info-model';
import {Peer} from '../../models/peer-model';

@Component({
  selector: 'app-network-difficulty',
  templateUrl: './network-difficulty.component.html',
  styleUrls: ['./network-difficulty.component.scss']
})
export class NetworkDifficultyComponent implements OnInit {
  netDif: TopchainInfo;
  peers: Peer[];

  constructor(private networkDifficultyService: NetworkDifficultyService) {
  }

  ngOnInit() {
    this.networkDifficultyService.getNetworkDifficulty().subscribe(x => this.netDif = x as TopchainInfo);
    this.networkDifficultyService.getPeers().subscribe(x => this.peers = x as Peer[]);
  }

}
