import { Block } from "../../models/block-model";
import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";

@Injectable()
export class BlockService{
    blocksUrl = 'http://localhost:5555/blocks';
    constructor(private http:HttpClient) { }

    public getBlocks() {
        return this.http.get<Array<Block>>(this.blocksUrl);
    }
}