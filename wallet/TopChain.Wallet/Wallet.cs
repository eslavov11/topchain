namespace TopChain.Wallet
{
    using Org.BouncyCastle.Asn1.Sec;
    using Org.BouncyCastle.Asn1.X509;
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
    using System.Linq;
    using System.Text;
    using Newtonsoft.Json;
    using TopChain.Wallet.Models;
    using Newtonsoft.Json.Serialization;

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
            return BitConverter.ToString(bytes).Replace("-", string.Empty).ToLower();
        }

        public static string EncodeECPointHexCompressed(ECPoint point)
        {
            BigInteger x = point.XCoord.ToBigInteger();
            return x.ToString(16) + Convert.ToInt32(!x.TestBit(0));
        }

        //almost made me rewrite RipeMD160Managed from http://referencesource.microsoft.com/mscorlib/system/security/cryptography/ripemd160managed.cs.html#http://referencesource.microsoft.com/mscorlib/system/security/cryptography/ripemd160managed.cs.html,http://referencesource.microsoft.com/mscorlib/system/security/cryptography/ripemd160managed.cs.html

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

        public static void RandomPrivateKeyToAddress()
        {
            Console.WriteLine("Private keys to address");

            var keyPair = GenerateRandomKeys();

            BigInteger privateKey = ((ECPrivateKeyParameters)keyPair.Private).D;
            Console.WriteLine("Private key : " + privateKey.ToString(16));

            ECPoint pubKey = ((ECPublicKeyParameters)keyPair.Public).Q;

            string pubKeyCompressed = EncodeECPointHexCompressed(pubKey);
            Console.WriteLine("Public key : " + pubKeyCompressed);

            string addr = CalculateRipeMD160Digest(pubKeyCompressed);
            Console.WriteLine("address: " + addr);
            Console.WriteLine("Please SAVE your private key somewhere that cannot be lost easily.");
        }

        public static string[] PrivateKeyToAddress(string privateKeyHex)
        {
            string[] result = new string[2];

            BigInteger privateKey = new BigInteger(privateKeyHex, 16);

            ECPoint pubKey = GetPublicKeyFromPrivateKey(privateKey);
            
            //result[0] holds compressed pub key
            result[0] = EncodeECPointHexCompressed(pubKey);
            //Console.WriteLine("Public key : " + result[0]);

            //result[1] holds address
            result[1]= CalculateRipeMD160Digest(result[0]);
            //Console.WriteLine("address: " + result[1]);
            return result;
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

        public static string CreateAndSignTransaction(string recipientAddress, decimal value,
            string iso8601datetime, string senderPrivKeyHex)
        {
            Console.WriteLine("Generate and sign a transaction");

            BigInteger privateKey = new BigInteger(senderPrivKeyHex, 16);

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
            //Console.WriteLine("Transaction (JSON): {0}", tranJson);

            byte[] tranHash = CalcSHA256(tranJson);
            //Console.WriteLine("Transaction hash(sha256): {0}", BytesToHex(tranHash));

            BigInteger[] tranSignature = SignTransaction(privateKey, tranHash);
            //Console.WriteLine("Transaction signature: [{0}, {1}]",
            //    tranSignature[0].ToString(16), tranSignature[1].ToString(16));

            Transaction tranSigned = new Transaction
            {
                FromAddress = senderAddress,
                ToAddress = recipientAddress,
                Value = value,
                DateCreated = iso8601datetime,
                SenderPubKey = senderPubKeyCompressed,
                TransactionHash = tranHash,
                SenderSignature = new string[]
                {
                    tranSignature[0].ToString(16),
                    tranSignature[1].ToString(16)
                }
            };

            string signedTransactionJson = JsonConvert.SerializeObject(tranSigned, Formatting.Indented,new JsonSerializerSettings
            {
                ContractResolver = new CamelCasePropertyNamesContractResolver()
            });
            //Console.WriteLine("json signed transaction :");
            //Console.WriteLine(signedTransactionJson);
            return signedTransactionJson;
        }


    }
}
