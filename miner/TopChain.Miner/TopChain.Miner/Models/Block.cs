namespace TopChain.Wallet.Models
{
    using System.Collections.Generic;
    public class Block
    {
        public int Index { get; set; }
        public List<Transaction> Transactions { get; set; }
        public int? Difficulty { get; set; }
        public string PrevBlockHash { get; set; }
        public string MinedBy { get; set; }
        public string BlockDataHash { get; set; }
        public int Reward { get; set; }
        public ulong? Nonce { get; set; }
        public string DateCreated { get; set; }
        public string BlockHash { get; set; }
    }
}
