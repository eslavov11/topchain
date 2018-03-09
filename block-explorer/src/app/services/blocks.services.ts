import {Block} from "../models/block-model";
import {HttpClient} from "@angular/common/http";
import {Injectable} from "@angular/core";
import Utils from "../utils/utils";

@Injectable()
export class BlockService {
  blocksUrl = Utils.NODE_URL + 'blocks';

  constructor(private http: HttpClient) {
  }

  public getBlocks() {
    return this.http.get<Array<Block>>(this.blocksUrl);
  }

  public getBlock(index: number) {
    return this.http.get<Block>(this.blocksUrl + '/' + index);
  }
}
