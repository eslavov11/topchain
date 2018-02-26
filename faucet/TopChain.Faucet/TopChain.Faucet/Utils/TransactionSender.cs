namespace TopChain.Faucet
{
    using Org.BouncyCastle.Asn1.Sec;
    using Org.BouncyCastle.Asn1.X9;
    using TopChain.Faucet.Models;
    using Newtonsoft.Json;
    using Newtonsoft.Json.Serialization;
    using Org.BouncyCastle.Math;
    using Org.BouncyCastle.Math.EC;
    using Org.BouncyCastle.Crypto.Parameters;
    using Org.BouncyCastle.Crypto.Signers;
    using System.Text;
    using Org.BouncyCastle.Crypto.Digests;
    using System;
    using System.Net.Http;

    public class TransactionSender
    {
        static readonly X9ECParameters curve = SecNamedCurves.GetByName("secp256k1");
        public static byte[] TranHash;

        public static async void SendSignedTransaction(string signedJsonTransaction)
        {
            string nodeIP = "127.0.0.1:5555";
            string postNewTransactionURL = "/transactions/send";
            try
            {
                using (HttpClient client = new HttpClient())
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

        public static ECPoint GetPublicKeyFromPrivateKey(BigInteger privKey)
        {
            ECPoint pubKey = curve.G.Multiply(privKey).Normalize();
            return pubKey;
        }

        public static string CreateAndSignTransaction(string recipientAddress, decimal value,
           string iso8601datetime)
        {

            string faucetPrivKey = "1235";
            BigInteger privateKey = new BigInteger(faucetPrivKey, 16);

            ECPoint pubKey = GetPublicKeyFromPrivateKey(privateKey);
            string senderPubKeyCompressed = EncodeECPointHexCompressed(pubKey);


            string senderAddress = CalculateRipeMD160Digest(senderPubKeyCompressed);

            Transaction tran = new Transaction
            {
                FromAddress = senderAddress,
                ToAddress = recipientAddress,
                Value = value,
                DateCreated = iso8601datetime,
                SenderPubKey = senderPubKeyCompressed,

            };
            string tranJson = JsonConvert.SerializeObject(tran, new JsonSerializerSettings
            {
                ContractResolver = new CamelCasePropertyNamesContractResolver()
            });

            TranHash = CalcSHA256(tranJson);

            BigInteger[] tranSignature = SignTransaction(privateKey, TranHash);

            Transaction tranSigned = new Transaction
            {
                FromAddress = senderAddress,
                ToAddress = recipientAddress,
                Value = value,
                DateCreated = iso8601datetime,
                SenderPubKey = senderPubKeyCompressed,
                TransactionHash = TranHash,
                SenderSignature = new string[]
                {
                    tranSignature[0].ToString(16),
                    tranSignature[1].ToString(16)
                }
            };

            string signedTransactionJson = JsonConvert.SerializeObject(tranSigned, Formatting.Indented, new JsonSerializerSettings
            {
                ContractResolver = new CamelCasePropertyNamesContractResolver()
            });
            return signedTransactionJson;
        }

        public static string EncodeECPointHexCompressed(ECPoint point)
        {
            BigInteger x = point.XCoord.ToBigInteger();
            return x.ToString(16) + Convert.ToInt32(!x.TestBit(0));
        }

        private static BigInteger[] SignTransaction(BigInteger privateKey, byte[] data)
        {
            ECDomainParameters ecSpec = new ECDomainParameters(curve.Curve, curve.G, curve.N, curve.H);
            ECPrivateKeyParameters keyParameters = new ECPrivateKeyParameters(privateKey, ecSpec);
            IDsaKCalculator kCalculator = new HMacDsaKCalculator(new Sha256Digest());
            ECDsaSigner signer = new ECDsaSigner(kCalculator);
            signer.Init(true, keyParameters);
            BigInteger[] signature = signer.GenerateSignature(data);
            return signature;
        }

        private static string CalculateRipeMD160Digest(string text)
        {
            byte[] bytes = Encoding.UTF8.GetBytes(text);

            //RIPEMD160Managed hashstring = new RIPEMD160Managed();
            //string tt = hashstring.ComputeHash(bytes)

            RipeMD160Digest digest = new RipeMD160Digest();
            digest.BlockUpdate(bytes, 0, bytes.Length);
            byte[] result = new byte[digest.GetDigestSize()];
            digest.DoFinal(result, 0);

            return BytesToHex(result);
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

        public static string BytesToHex(byte[] bytes)
        {
            return BitConverter.ToString(bytes).Replace("-", string.Empty).ToLower();
        }

    }
}
