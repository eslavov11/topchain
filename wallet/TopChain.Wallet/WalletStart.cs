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
                        //string address = Console.ReadLine();
                        string address = "f51362b7351ef62253a227a77751ad9b2302f911";
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
        }
        //TODO:Send a message when NODE isn't running
        public static void CreateAndSignTransaction()
        {
            Console.Write("Enter private key => ");
            string privateKey = "a1d15353e7dba2c2271c68bd4ea58032af8b46ce93d5b2354587f5ce58139c8e";
            //string privateKey = Console.ReadLine();
            Console.Write(privateKey);
            Console.WriteLine();
            Console.Write("Enter how much do you want to transfer => ");
            decimal valueToSend = 10.3m;
            //decimal valueToSend = decimal.Parse(Console.ReadLine());
            Console.WriteLine(valueToSend);

            string[] publicAndAddressPair = Wallet.PrivateKeyToAddress(privateKey);
            if (!BalanceChecker.CheckIfAddressHasBalance(publicAndAddressPair[1], valueToSend))
            {
                Console.WriteLine("Sorry,you don't have enough BTC in your wallet");
                Console.WriteLine("Your balance is -> {0}", BalanceChecker.GetAddressSavings(publicAndAddressPair[1]));
                return;
            }
            Console.WriteLine();
            Console.Write("Enter the address to which you want to transfer it => ");
            string recipientAddress = "f51362b7351ef62253a227a77751ad9b2302f911";
            //string recipientAddress = Console.ReadLine();
            Console.WriteLine(recipientAddress);

            string signedTransactionAsJson = Wallet.CreateAndSignTransaction(recipientAddress, valueToSend, DateTime.Now.ToString("0"), privateKey);
            TransactionSender.SendSignedTransaction(signedTransactionAsJson);
        }
    }
}
