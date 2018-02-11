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
        public static bool CheckIfAddressHasBalance(string privateKey, decimal valueToSend)
        {
            string addressToCheck = Wallet.AddressFromPrivateKey(privateKey);

            if (GetAddressSavings(addressToCheck) > valueToSend)
                return true;
            else
                return false;
        }

        public static decimal GetAddressSavings(string addressToCheck)
        {
            List<Block> blocks = GetCurrentBlocks();
            decimal currentSavingsForAddress = 0m;
            foreach (var block in blocks)
            {

                foreach (var transaction in block.Transactions)
                {

                    if (transaction.ToAddress == addressToCheck && transaction.TransferSuccessful == true)
                    {
                        currentSavingsForAddress += transaction.Value;
                        continue;
                    }
                    if (transaction.SenderPubKey != null)
                    {
                        string addressFromTrans = Wallet.PublicKeyToAddress(transaction.SenderPubKey);
                        if (addressFromTrans == addressToCheck && transaction.TransferSuccessful == true)
                        {
                            currentSavingsForAddress -= transaction.Value;
                        }
                    }
                }
            }
            return currentSavingsForAddress;
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
