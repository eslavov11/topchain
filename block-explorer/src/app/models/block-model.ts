import { Transaction } from "./transaction-model";

export class Block {
    index:number;
    transactions : Array<Transaction>;
    difficulty:number;
    prevBlockHash :string;
    minedBy:string;
    blockDataHash: string;
    nonce :number;
    dateCreated :string;
    blockHash:string;
}
