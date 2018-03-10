using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Net;
using System.Net.Http;
using TopChain.Wallet.Models;

namespace TopChain.Wallet.Utils
{
    public class BalanceChecker
    {
        public static long GetAddressSavings(string privateKeyToCheck)
        {
            string addressToCheck = Wallet.AddressFromPrivateKey(privateKeyToCheck);
            List<Block> blocks = GetCurrentBlocks();
            long currentSavingsForAddress = 0;
            if (blocks != null)
            {
                foreach (var block in blocks)
                {
                    foreach (var transaction in block.Transactions)
                    {

                        if (transaction.ToAddress == addressToCheck && transaction.TransferSuccessful == true)
                        {
                            currentSavingsForAddress += transaction.Value;
                            continue;
                        }
                        if (transaction.FromAddress == addressToCheck && transaction.TransferSuccessful == true)
                        {
                            currentSavingsForAddress -= transaction.Value;
                        }
                    }
                }
            }
            return currentSavingsForAddress/1000000;
        }
        public static List<Block> GetCurrentBlocks()
        {
            List<Block> result = null;
            string nodeIP = "127.0.0.1:5555";
            string getBlocksURL = "/blocks";
            try
            {
                using (var client = new HttpClient(new HttpClientHandler { AutomaticDecompression = DecompressionMethods.GZip | DecompressionMethods.Deflate }))
                {
                    client.BaseAddress = new Uri(string.Format("http://{0}", nodeIP));
                    HttpResponseMessage response = client.GetAsync(getBlocksURL).Result;
                    response.EnsureSuccessStatusCode();
                    string responseResult = response.Content.ReadAsStringAsync().Result;
                    result = JsonConvert.DeserializeObject<List<Block>>(responseResult);

                    string signedTranJson = JsonConvert.SerializeObject(result, Formatting.Indented);
                    //Console.WriteLine("testing serializing (JSON):");
                    //Console.WriteLine(signedTranJson);
                    //checking if its properly deserialized
                    //Console.WriteLine("Result: " + result[0].Index);
                    return result;
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
                return result;
            }
        }
    }
}
