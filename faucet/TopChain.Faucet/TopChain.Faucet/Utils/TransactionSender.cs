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
    using System.Linq;

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

        public static string GetPublicKeyCompressed(string privateKeyString)
        {
            BigInteger privateKey = new BigInteger(privateKeyString, 16);
            ECPoint pubKey = GetPublicKeyFromPrivateKey(privateKey);

            string pubKeyCompressed = EncodeECPointHexCompressed(pubKey);
            return pubKeyCompressed;
        }

        public static string CreateAndSignTransaction(string recipientAddress, long value,
           string iso8601datetime)
        {
            //faucet public key: 3cd938497b04562b35c001705ed287405da8aff2c486f70413350ef4f108ef9d8
            //faucet address: 9a959a9a2eb68ab550e0355dfef656a5e6016d71
            string faucetPrivKey = "2debcee8a64fb677dfd6c43423058b86810e7319c957b16bebb4fbf9883ac6b9";
            BigInteger hexPrivateKey = new BigInteger(faucetPrivKey, 16);

            string publicKey = GetPublicKeyCompressed(faucetPrivKey);
            string senderAddress = CalculateRipeMD160Digest(publicKey);

            Transaction tran = new Transaction
            {
                FromAddress = senderAddress,
                ToAddress = recipientAddress,
                SenderPubKey = publicKey,
                Value = value,
                Fee = 20,
                DateCreated = iso8601datetime,

            };
            string tranJson = JsonConvert.SerializeObject(tran, new JsonSerializerSettings
            {
                ContractResolver = new CamelCasePropertyNamesContractResolver()
            });

            TranHash = CalcSHA256(tranJson);
            string transHash = BytesToHex(TranHash);

            BigInteger[] tranSignature = SignTransaction(hexPrivateKey, TranHash);

            TransactionSign tranSigned = new TransactionSign
            {
                FromAddress = senderAddress,
                ToAddress = recipientAddress,
                Value = value,
                DateCreated = iso8601datetime,
                Fee = 20,
                SenderPubKey = publicKey,
                TransactionHash = transHash,
                SenderSignature = new string[]
                {
                    tranSignature[0].ToString(16),
                    tranSignature[1].ToString(16)
                }
            };

            string signedTransactionJson = JsonConvert.SerializeObject(tranSigned, new JsonSerializerSettings
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
            return string.Concat(bytes.Select(b => b.ToString("x2")));
        }
    }
}
