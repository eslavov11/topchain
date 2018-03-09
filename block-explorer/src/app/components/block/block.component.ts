import {Component, OnDestroy, OnInit} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Block} from '../../models/block-model';
import {BlockService} from '../../services/blocks.services';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-block',
  templateUrl: './block.component.html',
  styleUrls: ['./block.component.scss']
})
export class BlockComponent implements OnInit, OnDestroy {
  block: Block;
  index: number;
  private sub: any;

  constructor(private blockService: BlockService,
              private route: ActivatedRoute) {
  }

  ngOnInit() {
    this.sub = this.route.params.subscribe(params => {
      this.index = +params['index'];

      this.blockService.getBlock(this.index)
        .subscribe((data: Block) => {
          this.block = data as Block;
        });
    });
  }

  ngOnDestroy() {
    this.sub.unsubscribe();
  }
}
