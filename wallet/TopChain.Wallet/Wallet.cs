namespace TopChain.Wallet
{
    using Org.BouncyCastle.Asn1.Sec;
    using Org.BouncyCastle.Asn1.X9;
    using Org.BouncyCastle.Crypto;
    using Org.BouncyCastle.Crypto.Digests;
    using Org.BouncyCastle.Crypto.Generators;
    using Org.BouncyCastle.Crypto.Parameters;
    using Org.BouncyCastle.Crypto.Signers;
    using Org.BouncyCastle.Math;
    using Org.BouncyCastle.Math.EC;
    using Org.BouncyCastle.Security;
    using System;
    using System.Text;
    using Newtonsoft.Json;
    using TopChain.Wallet.Models;
    using Newtonsoft.Json.Serialization;
    using System.Linq;

    public class Wallet
    {
        static readonly X9ECParameters curve = SecNamedCurves.GetByName("secp256k1");

        public static ECPoint GetPublicKeyFromPrivateKey(BigInteger privKey)
        {
            ECPoint pubKey = curve.G.Multiply(privKey).Normalize();
            return pubKey;
        }

        public static AsymmetricCipherKeyPair GenerateRandomKeys()
        {
            int sizeOfKey = 256;
            ECKeyPairGenerator keyPairGenerator = new ECKeyPairGenerator();
            SecureRandom secureRandom = new SecureRandom();
            KeyGenerationParameters keyGenParam =
                new KeyGenerationParameters(secureRandom, sizeOfKey);

            keyPairGenerator.Init(keyGenParam);
            return keyPairGenerator.GenerateKeyPair();
        }

        public static string BytesToHex(byte[] bytes)
        {
            return string.Concat(bytes.Select(b => b.ToString("x2")));
        }

        //public static string EncodeECPointHexCompressed(ECPoint point)
        //{
        //    BigInteger x = point.XCoord.ToBigInteger();
        //    return x.ToString(16) + Convert.ToInt32(!x.TestBit(0));
        //}

        public static string EncodeECPointHexCompressed(ECPoint point)
        {
            var compressedPoint = point.GetEncoded(true);
            BigInteger biInt = new BigInteger(compressedPoint);

            return biInt.ToString(16);
        }

        private static string CalculateRipeMD160Digest(string text)
        {
            byte[] bytes = Encoding.UTF8.GetBytes(text);

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

        public static void RandomPrivateKeyToAddress()
        {
            Console.WriteLine("Private keys to address");

            var keyPair = GenerateRandomKeys();

            BigInteger privateKey = ((ECPrivateKeyParameters)keyPair.Private).D;
            Console.WriteLine("Private key : " + privateKey.ToString(16));

            ECPoint pubKey = GetPublicKeyFromPrivateKey(privateKey);

            string pubKeyCompressed = EncodeECPointHexCompressed(pubKey);
            Console.WriteLine("Public key : " + pubKeyCompressed);

            string addr = CalculateRipeMD160Digest(pubKeyCompressed);
            Console.WriteLine("address: " + addr);
            Console.WriteLine("Please SAVE your private key somewhere that cannot be lost easily.");
        }

        public static string AddressFromPrivateKey(string privateKeyHex)
        {
            BigInteger privateKey = new BigInteger(privateKeyHex, 16);

            ECPoint pubKey = GetPublicKeyFromPrivateKey(privateKey);
            string hexPubKey = EncodeECPointHexCompressed(pubKey);
            
            return CalculateRipeMD160Digest(hexPubKey);
        }

        public static string PublicKeyToAddress(string pubKey)
        {
            return CalculateRipeMD160Digest(pubKey);
        }

        /// <summary>
        /// Calculates deterministic ECDSA signature (with HMAC-SHA256), based on secp256k1 and RFC-6979.
        /// </summary>
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

        public static string GetPublicKeyCompressed(string privateKeyString)
        {
            BigInteger privateKey = new BigInteger(privateKeyString, 16);
            ECPoint pubKey = GetPublicKeyFromPrivateKey(privateKey);

            string pubKeyCompressed = EncodeECPointHexCompressed(pubKey);
            return pubKeyCompressed;
        }

        public static string CreateAndSignTransaction(string recipientAddress, long value,
            string iso8601datetime, string senderPrivKeyHex)
        {
            Console.WriteLine("Generate and sign a transaction");

            BigInteger hexPrivateKey = new BigInteger(senderPrivKeyHex, 16);

            string publicKey = GetPublicKeyCompressed(senderPrivKeyHex);
            string senderAddress = CalculateRipeMD160Digest(publicKey);

            TransactionRaw tran = new TransactionRaw
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
            
            byte[] txHash = CalcSHA256(tranJson);
            string transHash = BytesToHex(txHash);

            BigInteger[] tranSignature = SignTransaction(hexPrivateKey, txHash);

            Transaction tranSigned = new Transaction
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

            string signedTransactionJson = JsonConvert.SerializeObject(tranSigned,new JsonSerializerSettings
            {
                ContractResolver = new CamelCasePropertyNamesContractResolver()
            });
            return signedTransactionJson;
        }


    }
}
