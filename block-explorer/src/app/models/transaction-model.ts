export class Transaction {
    fromAddress :string;
    toAddress :string;
    value :number;
    dateCreated :string
    senderPubKey :string
    senderSignature : string[]
    transactionHash : Uint32Array[] 
    transferSuccessful  : boolean 
}
