import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Block } from '../../models/block-model';
import { BlockService } from '../../services/blocks.services';
@Component({
  selector: 'app-blocks',
  templateUrl: './blocks.component.html',
  styleUrls: ['./blocks.component.scss']
})
export class BlocksComponent implements OnInit {
  blocks:Array<Block>;
  constructor(private blockService:BlockService) { }

  ngOnInit() {
    this.blockService.getBlocks()
                          .subscribe((data:Array<Block>)=>{ this.blocks = data as Array<Block>; });
  }
}
