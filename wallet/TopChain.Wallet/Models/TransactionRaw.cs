namespace TopChain.Wallet.Models
{
    public class TransactionRaw
    {
        public string FromAddress { get; set; }
        public string ToAddress { get; set; }
        public string SenderPubKey { get; set; }
        public long Value { get; set; }
        public int Fee { get; set; }
        public string DateCreated { get; set; }
    }
}
