import {Transaction} from "./transaction-model";

export class Block {
  index: number;
  transactions: Array<Transaction>;
  difficulty: number;
  previousBlockHash: string;
  minedBy: string;
  blockDataHash: string;
  nonce: number;
  dateCreated: Date;
  blockHash: string;
}
