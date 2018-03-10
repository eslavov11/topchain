namespace TopChain.Miner
{
    using Newtonsoft.Json;
    using Newtonsoft.Json.Serialization;
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
        private static Block blockToMine;

        private static CancellationTokenSource cancelTasks;

        static void Main(string[] args)
        {
            //string minerAddress = Console.ReadLine();
            //private keys for miner address to TEST
            //string privateKey = "8f3ed57916ab3f6e672b77ca09fb80d1bedccc255ad14ee07a676803fe93e977";
            //string pubKey = "2be3c6750dc1bfca520afc66bb44760505883c05488e3a62e7721fe06a9bccc9d";
            var availableThreads = Environment.ProcessorCount;
            ulong startingNonce = ulong.MinValue + 1;

            string minerAddress = "ca406724d901bc69e9f75d34f15563a03e163db9";

            blockToMine = GetBlock(minerAddress);

            while (true)
            {
                cancelTasks = new CancellationTokenSource(); 
                var taskList = new List<Task<ResultWrapper>>();

                GlobalCounter.Reset();
                var sw = Stopwatch.StartNew();


                for (int i = 0; i < availableThreads; i++)
                {
                    taskList.Add(Task.Run(() =>
                    {
                        return StartMining(blockToMine, minerAddress, startingNonce += 1000000);

                    }, cancelTasks.Token));

                }

                Task.WaitAny(taskList.ToArray());
                var foundHash = taskList.FirstOrDefault(x => x.Result != null);
                cancelTasks.Cancel();

                if (foundHash != null)
                {
                    SubmitPoW(foundHash.Result);
                }

                sw.Stop();
                var kiloHashesPerSecond = (GlobalCounter.Value / sw.Elapsed.TotalSeconds) / 1000;
                Console.WriteLine("kHs => {0} ", kiloHashesPerSecond);
                blockToMine = GetBlock(minerAddress);
            }
        }

        public static ResultWrapper StartMining(Block blockToMine, string minerAddress, ulong nonce)
        {
            string blockDataHash = string.Empty;
            ResultWrapper result = null;

            result = MineBlock(blockToMine, nonce);

            return result;
        }

        public static ResultWrapper MineBlock(Block blockToMine, ulong nonce)
        {
            if (cancelTasks.IsCancellationRequested)
            {
                return null;
            }
            ResultWrapper result = new ResultWrapper();
            string dateCreated = DateTime.Now.ToString("yyyy-MM-ddTHH:mm:ssZ");
            if (blockToMine == null) return null;
            int difficulty = blockToMine.Difficulty.Value;
            var nextHash = CalcSHA256(blockToMine.BlockDataHash + dateCreated + nonce);

            string nextHashSubstring = ConvertToBase16Fast2(nextHash).Substring(0, difficulty);
            string difficultyToAchieve = new String('0', difficulty);
            while (nextHashSubstring != difficultyToAchieve)
            {
                nonce++;
                dateCreated = DateTime.Now.ToString("yyyy-MM-ddTHH:mm:ssZ");
                nextHash = CalcSHA256(blockToMine.BlockDataHash + dateCreated + nonce);
                nextHashSubstring = ConvertToBase16Fast2(nextHash).Substring(0, difficulty);
            }
            
            result.Nonce = nonce;
            result.DateCreated = dateCreated;
            result.BlockDataHash = blockToMine.BlockDataHash;

            return result;
        }

        private static byte[] CalcSHA256(string text)
        {
            byte[] bytes = Encoding.UTF8.GetBytes(text);
            Sha256Digest digest = new Sha256Digest();
            digest.BlockUpdate(bytes, 0, bytes.Length);
            byte[] result = new byte[digest.GetDigestSize()];
            digest.DoFinal(result, 0);
            //increment global counter
            GlobalCounter.Increment();
            return result;
        }

        public static Block GetBlock(string minerAddress)
        {
            Block result = null;
            string nodeIP = "127.0.0.1:5555";

            string getBlocksURL = string.Format("/mining/get-mining-job/{0}", minerAddress);
            try
            {
                string url = string.Format("http://{0}{1}", nodeIP, getBlocksURL);
                string res = new WebClient().DownloadString(url);
                Block obj = JsonConvert.DeserializeObject<Block>(res);

                return obj;
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
            //string nodeIP = "127.0.0.1:5555";

            //string getBlocksURL = string.Format("/mining/get-block/{0}{1}", result.DateCreated,result.Nonce);

            var dataToSend = JsonConvert.SerializeObject(result, new JsonSerializerSettings
            {
                ContractResolver = new CamelCasePropertyNamesContractResolver()
            });
            try
            {
                using (var client = new WebClient())
                {
                    client.Headers.Add(HttpRequestHeader.ContentType, "application/json");
                    client.Headers.Add(HttpRequestHeader.Accept, "application/json");

                    var res = client.UploadString("http://127.0.0.1:5555/mining/submit-mined-block/", "POST", dataToSend);

                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex);
            }
            Console.WriteLine("Submited Date  {0} ", result.DateCreated);
            Console.WriteLine("Submited Nonce  {0} ", result.Nonce);
        }


        private static readonly string[] _base16CharTable = new[]
        {
            "00", "01", "02", "03", "04", "05", "06", "07",
            "08", "09", "0A", "0B", "0C", "0D", "0E", "0F",
            "10", "11", "12", "13", "14", "15", "16", "17",
            "18", "19", "1A", "1B", "1C", "1D", "1E", "1F",
            "20", "21", "22", "23", "24", "25", "26", "27",
            "28", "29", "2A", "2B", "2C", "2D", "2E", "2F",
            "30", "31", "32", "33", "34", "35", "36", "37",
            "38", "39", "3A", "3B", "3C", "3D", "3E", "3F",
            "40", "41", "42", "43", "44", "45", "46", "47",
            "48", "49", "4A", "4B", "4C", "4D", "4E", "4F",
            "50", "51", "52", "53", "54", "55", "56", "57",
            "58", "59", "5A", "5B", "5C", "5D", "5E", "5F",
            "60", "61", "62", "63", "64", "65", "66", "67",
            "68", "69", "6A", "6B", "6C", "6D", "6E", "6F",
            "70", "71", "72", "73", "74", "75", "76", "77",
            "78", "79", "7A", "7B", "7C", "7D", "7E", "7F",
            "80", "81", "82", "83", "84", "85", "86", "87",
            "88", "89", "8A", "8B", "8C", "8D", "8E", "8F",
            "90", "91", "92", "93", "94", "95", "96", "97",
            "98", "99", "9A", "9B", "9C", "9D", "9E", "9F",
            "A0", "A1", "A2", "A3", "A4", "A5", "A6", "A7",
            "A8", "A9", "AA", "AB", "AC", "AD", "AE", "AF",
            "B0", "B1", "B2", "B3", "B4", "B5", "B6", "B7",
            "B8", "B9", "BA", "BB", "BC", "BD", "BE", "BF",
            "C0", "C1", "C2", "C3", "C4", "C5", "C6", "C7",
            "C8", "C9", "CA", "CB", "CC", "CD", "CE", "CF",
            "D0", "D1", "D2", "D3", "D4", "D5", "D6", "D7",
            "D8", "D9", "DA", "DB", "DC", "DD", "DE", "DF",
            "E0", "E1", "E2", "E3", "E4", "E5", "E6", "E7",
            "E8", "E9", "EA", "EB", "EC", "ED", "EE", "EF",
            "F0", "F1", "F2", "F3", "F4", "F5", "F6", "F7",
            "F8", "F9", "FA", "FB", "FC", "FD", "FE", "FF"
        };

        public static string ConvertToBase16Fast2(IList<byte> input)
        {
            if (input == null || input.Count <= 0)
                return string.Empty;

            var stringBuilder = new StringBuilder(input.Count * 2);

            for (var i = 0; i < input.Count; ++i)
                stringBuilder.Append(_base16CharTable[input[i]]);

            return stringBuilder.ToString();
        }
    }
}
