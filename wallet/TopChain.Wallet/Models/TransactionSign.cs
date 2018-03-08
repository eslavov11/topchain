namespace TopChain.Wallet.Models
{
    public class TransactionSign
    {
        public string FromAddress { get; set; }
        public string ToAddress { get; set; }
        public string SenderPubKey { get; set; }
        public long Value { get; set; }
        public int Fee { get; set; }
        public string DateCreated { get; set; }
        public string[] SenderSignature { get; set; }
        public string TransactionHash { get; set; }
    }
}
