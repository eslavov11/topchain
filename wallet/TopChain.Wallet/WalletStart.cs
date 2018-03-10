namespace TopChain.Wallet
{
    using System;
    using TopChain.Wallet.Utils;

    class WalletStart
    {
        static void Main(string[] args)
        {
            int input = 0;
            while (true)
            {
                //TODO: Wallet new | check balance | send transaction
                Console.WriteLine("Press 1 to generate new wallet.");
                Console.WriteLine("Press 2 to check account balance from Private key.");
                Console.WriteLine("Press 3 to send transaction.");
                bool flag = false;
                switch (input = int.Parse(Console.ReadLine()))
                {
                    case 1:
                        Console.Clear();
                        Wallet.RandomPrivateKeyToAddress();
                        flag = true;
                        break;
                    case 2:
                        Console.Clear();
                        Console.Write("Input private key to check balance: ");
                        string address = Console.ReadLine();
                        //string address = "4c13ab8c6ae1b1878fe9270954d4afa3c69863ec";
                        Console.WriteLine("Current BTC - {0}", BalanceChecker.GetAddressSavings(address));
                        flag = true;
                        break;
                    case 3:
                        Console.Clear();

                        CreateAndSignTransaction();

                        flag = true;
                        break;
                    default:
                        Console.Clear();
                        Console.WriteLine("Please pick another option");
                        Console.WriteLine("---------------------------");

                        break;
                }
                if (flag) break;
            }
            Console.ReadLine();
        }
        public static void CreateAndSignTransaction()
        
{
            Console.Write("Enter private key => ");
            //set faucet private key to test 
            //string privateKey = "2debcee8a64fb677dfd6c43423058b86810e7319c957b16bebb4fbf9883ac6b9";
            string privateKey = Console.ReadLine();
            Console.Write(privateKey);
            Console.WriteLine();
            Console.Write("Enter how much do you want to transfer => ");
            //long valueToSend = 1;
            long valueToSend = long.Parse(Console.ReadLine());
            valueToSend *= 1000000;
            Console.WriteLine(valueToSend);

            string address = Wallet.AddressFromPrivateKey(privateKey);
            long addrSavings = BalanceChecker.GetAddressSavings(address);
            if (addrSavings < valueToSend)
            {
                Console.WriteLine("Sorry,you don't have enough BTC in your wallet");
                Console.WriteLine("Your balance is -> {0}", addrSavings);
                return;
            }
            Console.WriteLine();
            Console.Write("Enter the address to which you want to transfer it => ");
            //string recipientAddress = "f51362b7351ef62253a227a77751ad9b2302f911";
            string recipientAddress = Console.ReadLine();
            Console.WriteLine(recipientAddress);
            string iso8601standard = DateTime.UtcNow.ToString("yyyy-MM-ddTHH:mm:ssZ");
            string signedTransactionAsJson = Wallet.CreateAndSignTransaction(recipientAddress, valueToSend, iso8601standard, privateKey);
            TransactionSender.SendSignedTransaction(signedTransactionAsJson);

            Console.Clear();
            Console.WriteLine("Transaction passed successfuly.");
        }
    }
}
