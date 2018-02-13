namespace TopChain.Miner
{
    using Newtonsoft.Json;
    using Org.BouncyCastle.Crypto.Digests;
    using System;
    using System.Collections.Generic;
    using System.Diagnostics;
    using System.Linq;
    using System.Net;
    using System.Net.Http;
    using System.Runtime.InteropServices;
    using System.Text;
    using System.Threading;
    using System.Threading.Tasks;
    using TopChain.Miner.Models;
    using TopChain.Wallet.Models;

    class Program
    {
        public static class GlobalCounter
        {
            public static int Value { get; private set; }

            public static void Increment()
            {
                Value = GetNextValue(Value);
            }

            private static int GetNextValue(int curValue)
            {
                return Interlocked.Increment(ref curValue);
            }

            public static void Reset()
            {
                Value = 0;
            }
        }

        static void Main(string[] args)
        {
            //string minerAddress = Console.ReadLine();
            //private keys for miner address to TEST
            //string privateKey = "36bea7f877c979c9e8c7b4a5571bad692788ee3a41157d925d4a4c6f9655c420";
            //string pubKey = "bbcd517721426fccc243c6e482d91f316b2416a1228516fad991f29018bc99981";

            string minerAddress = "d08498041433013fe278f9cd63c53e6b6f0e8033";

            var availableThreads = Environment.ProcessorCount - 1;

            var taskList = new List<Task<ResultWrapper>>();

            //todo:how to split nonce for every thread

            ulong startingNonce = ulong.MinValue+1;

            for (int i = 0; i < availableThreads; i++)
            {
                taskList.Add(StartMining(minerAddress, startingNonce+(ulong)(5000000/availableThreads)));
                taskList[i].Start();
            }
        }
        //public static void MethodToExecute(Block blockToMine,Stopwatch sw)
        //{
        //    bool _isRunning = true;
        //    while (_isRunning)
        //    {
        //        
                //todo
        //        if (MineBlock(blockToMine) !=null)
        //        {
        //            Console.WriteLine(sw.Elapsed.TotalSeconds); 
        //            break;
        //        }
                
        //    }
        //}

        public static async Task<ResultWrapper> StartMining(string minerAddress,ulong nonce)
        {
            Block blockToMine = GetBlock(minerAddress);
            string blockDataHash = string.Empty;
            while (true)
            {
                GlobalCounter.Reset();
                var sw = Stopwatch.StartNew();
                if (blockDataHash!=blockToMine.BlockDataHash)
                {
                    blockDataHash = blockToMine.BlockDataHash;
                    ResultWrapper result = await MineBlock(blockToMine,nonce);
                    sw.Stop();
                    var kiloHashesPerSecond = (GlobalCounter.Value / sw.Elapsed.TotalSeconds) / 1000;
                    Console.WriteLine("kHs => {0} ",kiloHashesPerSecond);
                    //SubmitPoW(result);
                }
                else
                {
                    blockToMine = GetBlock(minerAddress);
                }
            }
        }
        

        public static async Task<ResultWrapper> MineBlock(Block blockToMine,ulong nonce)
        {
            ResultWrapper result = new ResultWrapper();
            string dateCreated = DateTime.UtcNow.ToString("yyyy-MM-ddTHH:mm:ssZ");
            int difficulty = blockToMine.Difficulty.Value;
            var nextHash = CalcSHA256(blockToMine.BlockDataHash + dateCreated + nonce);

            string nextHashSubstring = BytesToHex(nextHash).Substring(0,difficulty);
            string difficultyToAchieve = string.Join(string.Empty, Enumerable.Repeat("0", difficulty).ToArray());

            while (nextHashSubstring != difficultyToAchieve)
            {
                nonce++;
                dateCreated = DateTime.UtcNow.ToString("yyyy-MM-ddTHH:mm:ssZ");
                nextHash = CalcSHA256(blockToMine.BlockDataHash + dateCreated + nonce);
                GlobalCounter.Increment();

                nextHashSubstring = BytesToHex(nextHash).Substring(0, difficulty);
                
            }
            result.Nonce = nonce;
            result.DateCreated = dateCreated;

            Console.WriteLine(BytesToHex(nextHash));
            Console.WriteLine(result.DateCreated);
            Console.WriteLine(result.Nonce);
            return result;
        }

        private static byte[] CalcSHA256(string text)
        {
            byte[] bytes = Encoding.UTF8.GetBytes(text);
            Sha256Digest digest = new Sha256Digest();
             digest.BlockUpdate(bytes, 0, bytes.Length);
            byte[] result = new byte[digest.GetDigestSize()];
            digest.DoFinal(result, 0);
            return result;
        }

        public static Block GetBlock(string minerAddress)
        {
            Block result = null;
            string nodeIP = "127.0.0.1:5555";

            string getBlocksURL = string.Format("/mining/get-block/{0}", minerAddress);
            try
            {
                using (var client = new HttpClient(new HttpClientHandler { AutomaticDecompression = DecompressionMethods.GZip | DecompressionMethods.Deflate }))
                {
                    client.BaseAddress = new Uri(string.Format("http://{0}{1}", nodeIP,getBlocksURL));
                    HttpResponseMessage response = client.GetAsync(getBlocksURL).Result;
                    response.EnsureSuccessStatusCode();
                    string responseResult = response.Content.ReadAsStringAsync().Result;
                    result = JsonConvert.DeserializeObject<Block>(responseResult);

                    //string signedTranJson = JsonConvert.SerializeObject(result, Formatting.Indented);

                    //Console.WriteLine("testing serializing (JSON):");
                    //Console.WriteLine(signedTranJson);
                    //checking if its properly deserialized
                    //Console.WriteLine("Result: " + result[0].Index);
                    result.Difficulty = 5;
                    return result;
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
                return result;
            }
        }

        public static string BytesToHex(byte[] bytes)
        {
            return BitConverter.ToString(bytes).Replace("-", string.Empty).ToLower();
        }


        public static void SubmitPoW(ResultWrapper result)
        {
            Console.WriteLine("Submited Date  {0} ",result.DateCreated);
            Console.WriteLine("Submited Nonce  {0} ",result.Nonce);
        }
    }
}
