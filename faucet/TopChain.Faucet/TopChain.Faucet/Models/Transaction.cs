namespace TopChain.Faucet.Models
{
    public class Transaction
    {
        public string FromAddress { get; set; }
        public string ToAddress { get; set; }
        public decimal Value { get; set; }
        public string DateCreated { get; set; }
        public string SenderPubKey { get; set; }
        public string[] SenderSignature { get; set; }
        public byte[] TransactionHash { get; set; }
        public bool TransferSuccessful { get; set; }
    }
}
