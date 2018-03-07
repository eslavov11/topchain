package com.topchain.node.util;

import org.bouncycastle.asn1.sec.SECNamedCurves;
import org.bouncycastle.asn1.x9.X9ECParameters;
import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.crypto.signers.DSAKCalculator;
import org.bouncycastle.crypto.signers.ECDSASigner;
import org.bouncycastle.crypto.signers.HMacDSAKCalculator;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

public class CryptoUtils {
    private static X9ECParameters curve = SECNamedCurves.getByName("secp256k1");

    public static boolean verifySignature(String message,
                                          String[] signature,
                                          String publicKey) {
        ECDomainParameters ecSpec = new ECDomainParameters(curve.getCurve(),
                curve.getG(), curve.getN(), curve.getH());
        DSAKCalculator kCalculator = new HMacDSAKCalculator(new SHA256Digest());

        org.bouncycastle.math.ec.ECPoint point = DecodeECPointPublicKey(publicKey);

        ECPublicKeyParameters keyParameters = new ECPublicKeyParameters(point, ecSpec);

        ECDSASigner signer = new ECDSASigner(kCalculator);
        signer.init(false, keyParameters);

        BigInteger pubKey1 = new BigInteger(signature[0], 16);
        BigInteger pubKey2 = new BigInteger(signature[1], 16);

        byte[] tranHash = calcSHA256(message);

        return signer.verifySignature(tranHash, pubKey1, pubKey2);
    }

    private static byte[] calcSHA256(String text) {
        byte[] bytes = text.getBytes(StandardCharsets.UTF_8);
        SHA256Digest digest = new SHA256Digest();
        digest.update(bytes, 0, bytes.length);
        byte[] result = new byte[digest.getDigestSize()];
        digest.doFinal(result, 0);
        return result;
    }

    private static org.bouncycastle.math.ec.ECPoint DecodeECPointPublicKey(String publicKey) {
        BigInteger bigInt = new BigInteger(publicKey, 16);
        byte[] compressedKey = bigInt.toByteArray();

        org.bouncycastle.math.ec.ECPoint point = curve.getCurve().decodePoint(compressedKey);
        return point;
    }

    private void exampleSignature() {
        String pbKey = "30094e9b9b8dc27015728d1b74c7312e30a857483ace35e1cced2b100ac343b8a";
        String message = "{\"FromAddress\":\"710eb9c05a0717cd5f5ce7fc7b17fe616fdc40d0\",\"ToAddress\":\"f51362b7351ef62253a227a77751ad9b2302f911\",\"Amount\":10,\"Fee\":100000,\"DateCreated\":\"2018-03-07T09:44:48.2629131+02:00\",\"Comment\":null}";
        String[] signature = {"f8e637eb315e9f50f14fa4c84e335ce5b103abbe61263c8ccca5c36cd02dbd05",
                "69028ffae6e9d05df8228a76baff8fa96f8d5470c8de2597fb9d404f063c5e41"};


        System.out.println(verifySignature(message, signature, pbKey));
    }
}
