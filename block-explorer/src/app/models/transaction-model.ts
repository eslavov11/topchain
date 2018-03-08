export class Transaction {
  fromAddress: string;
  toAddress: string;
  value: number;
  fee: number;
  dateCreated: string;
  senderPubKey: string;
  senderSignature: string[];
  transactionHash: string;
  minedInBlockIndex: number;
  transferSuccessful: boolean;
}
