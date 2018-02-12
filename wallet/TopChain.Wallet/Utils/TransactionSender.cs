using Newtonsoft.Json;
using System;
using System.Net;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;

namespace TopChain.Wallet.Utils
{
    public class TransactionSender
    {
        public static async void SendSignedTransaction(string signedJsonTransaction)
        {
            string nodeIP = "127.0.0.1:5555";
            string postNewTransactionURL = "/transactions/send";
            try
            {
                using(HttpClient client = new HttpClient())
                {
                    var stringContent = new StringContent(signedJsonTransaction, Encoding.UTF8, "application/json");
                    var response = await client.PostAsync(string.Format("http://{0}{1}", nodeIP, postNewTransactionURL), stringContent);
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }
    }
}
