package com.topchain.node.config;

import com.topchain.node.entity.Block;
import com.topchain.node.entity.Node;
import com.topchain.node.entity.Transaction;
import com.topchain.node.model.viewModel.NodeInfoViewModel;
import org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util;
import org.bouncycastle.jce.ECPointUtil;
import org.bouncycastle.jce.spec.ECNamedCurveParameterSpec;
import org.bouncycastle.jce.spec.ECNamedCurveSpec;
import org.bouncycastle.math.ec.ECCurve;
import org.bouncycastle.util.encoders.Hex;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.ECPublicKey;
import java.security.spec.*;
import java.util.Arrays;
import java.util.Date;

import static com.topchain.node.util.NodeUtils.*;


import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.sql.Timestamp;

import org.bouncycastle.jce.ECNamedCurveTable;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ECParameterSpec;

@Configuration
public class AppConfig {
    private static final int GENESIS_BLOCK_INDEX = 0;
    private static final String INIT_MINER_ADDRESS = "0000000000000000000000000000000000000000";

    @Value("${node.about}")
    private String nodeAbout;

    @Value("${node.name}")
    private String nodeName;

    @Value("${node.coins}")
    private Long nodeCoins;

    @Value("${faucet.coins}")
    private Long faucetCoins;

    @Value("${network.difficulty}")
    private int nodeDifficulty;

    private String FAUCET_ADDRESS = "f51362b7351ef62253a227a77751ad9b2302f911";

    @Bean
    public ModelMapper getModelMapper() {
        return new ModelMapper();
    }

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @Bean
    public Node generateNode() {
        exampleSignature();


        Node node = new Node();
        node.setDifficulty(nodeDifficulty);
        node.addBlock(createGenesisBlock());

        return node;
    }

    public static KeyPair GenerateKeys() throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException {
//	Other named curves can be found in http://www.bouncycastle.org/wiki/display/JA1/Supported+Curves+%28ECDSA+and+ECGOST%29
        ECParameterSpec ecSpec = ECNamedCurveTable.getParameterSpec("B-571");

        KeyPairGenerator g = KeyPairGenerator.getInstance("ECDSA", "BC");

        g.initialize(ecSpec, new SecureRandom());

        return g.generateKeyPair();
    }


    public static byte[] GenerateSignature(String plaintext, KeyPair keys) throws SignatureException, UnsupportedEncodingException, InvalidKeyException, NoSuchAlgorithmException, NoSuchProviderException {
        Signature ecdsaSign = Signature.getInstance("SHA256withECDSA", "BC");
        ecdsaSign.initSign(keys.getPrivate());
        ecdsaSign.update(plaintext.getBytes("UTF-8"));
        byte[] signature = ecdsaSign.sign();
        System.out.println(signature.toString());
        return signature;
    }

    public static boolean ValidateSignature(String plaintext, KeyPair pair, byte[] signature) throws SignatureException, InvalidKeyException, UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchProviderException {
        Signature ecdsaVerify = Signature.getInstance("SHA256withECDSA", "BC");
        ecdsaVerify.initVerify(pair.getPublic());
        ecdsaVerify.update(plaintext.getBytes("UTF-8"));
        return ecdsaVerify.verify(signature);
    }

//    public static String savePublicKey(PublicKey publ) throws GeneralSecurityException {
//        KeyFactory fact = KeyFactory.getInstance("DSA");
//        X509EncodedKeySpec spec = fact.getKeySpec(publ,
//                X509EncodedKeySpec.class);
//        return base64Encode(spec.getEncoded());
//    }
//
//    public static PublicKey loadPublicKey(String stored) throws GeneralSecurityException {
//        byte[] data = base64Decode(stored);
//        X509EncodedKeySpec spec = new X509EncodedKeySpec(data);
//        KeyFactory fact = KeyFactory.getInstance("DSA");
//        return fact.generatePublic(spec);
//    }

    private PublicKey getPublicKeyFromBytes(byte[] pubKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        ECNamedCurveParameterSpec spec = ECNamedCurveTable.getParameterSpec("secp256k1");
        KeyFactory kf = KeyFactory.getInstance("ECDSA", new BouncyCastleProvider());
        ECNamedCurveSpec params = new ECNamedCurveSpec("secp256k1",
                spec.getCurve(), spec.getG(), spec.getN());
        ECPoint point = ECPointUtil.decodePoint(params.getCurve(), pubKey);
        ECPublicKeySpec pubKeySpec = new ECPublicKeySpec(point, params);
        ECPublicKey pk = (ECPublicKey) kf.generatePublic(pubKeySpec);
        return pk;
    }

    private int PUBLIC_KEY_LENGTH = 65;
    private int PRIVATE_KEY_LENGTH = 64;

    private String getPublicKeyAsHex(PublicKey publicKey) {

        ECPublicKey ecPublicKey = (ECPublicKey) publicKey;
        ECPoint ecPoint = ecPublicKey.getW();

        byte[] publicKeyBytes = new byte[PUBLIC_KEY_LENGTH];
        writeToStream(publicKeyBytes, 0, ecPoint.getAffineX(), PRIVATE_KEY_LENGTH);
        writeToStream(publicKeyBytes, PRIVATE_KEY_LENGTH, ecPoint.getAffineY(), PRIVATE_KEY_LENGTH);

        String hex = Hex.toHexString(publicKeyBytes);

        System.out.println("Public key bytes: " + Arrays.toString(publicKeyBytes));
        System.out.println("Public key hex: " + hex);

        return hex;
    }

    /**
     * This method converts the uncompressed raw EC public key into java.security.interfaces.ECPublicKey
     * @param rawPubKey
     * @param curveName
     * @return java.security.interfaces.ECPublicKey
     */
    public ECPublicKey ucPublicKeyToPublicKey(String rawPubKey, String curveName) throws NoSuchAlgorithmException {
        byte[] rawPublicKey = toByte(rawPubKey);
        ECPublicKey ecPublicKey = null;
        KeyFactory kf = null;

        ECNamedCurveParameterSpec ecNamedCurveParameterSpec = ECNamedCurveTable.getParameterSpec(curveName);
        ECCurve curve = ecNamedCurveParameterSpec.getCurve();
        EllipticCurve ellipticCurve = EC5Util.convertCurve(curve, ecNamedCurveParameterSpec.getSeed());
        java.security.spec.ECPoint ecPoint = ECPointUtil.decodePoint(ellipticCurve, rawPublicKey);
        java.security.spec.ECParameterSpec ecParameterSpec = EC5Util.convertSpec(ellipticCurve, ecNamedCurveParameterSpec);
        java.security.spec.ECPublicKeySpec publicKeySpec = new java.security.spec.ECPublicKeySpec(ecPoint, ecParameterSpec);

        kf = java.security.KeyFactory.getInstance("EC");

        try {
            ecPublicKey = (ECPublicKey) kf.generatePublic(publicKeySpec);
        } catch (Exception e) {
            System.out.println("Caught Exception public key: " + e.toString());
        }

        return ecPublicKey;
    }

    public static byte[] toByte(String hex) {
        if (hex == null)
            return null;
        hex = hex.replaceAll("\\s", "");
        byte[] buffer = null;
        if (hex.length() % 2 != 0) {
            hex = "0" + hex;
        }
        int len = hex.length() / 2;
        buffer = new byte[len];
        for (int i = 0; i < len; i++) {
            buffer[i] = (byte) Integer.parseInt(
                    hex.substring(i * 2, i * 2 + 2), 16);
        }
        return buffer;
    }

    private void writeToStream(byte[] stream, int start, BigInteger value, int size) {
        byte[] data = value.toByteArray();
        int length = Math.min(size, data.length);
        int writeStart = start + size - length;
        int readStart = data.length - length;
        System.arraycopy(data, readStart, stream, writeStart, length);
    }


    private void exampleSignature() {
        try {
            Security.addProvider(new BouncyCastleProvider());

            String plaintext = "Simple plain text";
            KeyPair keys = GenerateKeys();
//		System.out.println(keys.getPublic().toString());
//		System.out.println(keys.getPrivate().toString());

            KeyFactory fact = KeyFactory.getInstance("ECDSA", "BC");
            PublicKey pub = fact.generatePublic(new X509EncodedKeySpec(keys.getPublic().getEncoded()));
//
//            String publicKey = getPublicKeyAsHex(keys.getPublic());
//
//            byte[] convertedBack = publicKey.getBytes(StandardCharsets.UTF_8);

            //TODO: should be working
            //PublicKey publicKeyFromStr = ucPublicKeyToPublicKey("", "secp256k1");

            KeyPair keysPublic = new KeyPair(pub, null);


            byte[] signature = GenerateSignature(plaintext, keys);

            boolean isValidated = ValidateSignature(plaintext, keysPublic, signature);
            System.out.println("Result: " + isValidated);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Block createGenesisBlock() {
        Block genesis = new Block();
        genesis.setIndex(GENESIS_BLOCK_INDEX);
        Transaction faucetTransacton = new Transaction();
        faucetTransacton.setFromAddress(INIT_MINER_ADDRESS);
        faucetTransacton.setToAddress(FAUCET_ADDRESS);
        faucetTransacton.setValue(faucetCoins);
        faucetTransacton.setDateCreated(new Date());
        faucetTransacton.setMinedInBlockIndex(GENESIS_BLOCK_INDEX);
        faucetTransacton.setTransferSuccessful(true);

        Transaction t1 = new Transaction();
        t1.setFromAddress(INIT_MINER_ADDRESS);
        t1.setToAddress("f51362b7351ef62253a227a77751ad9b2302f911");
        t1.setValue(100L);
        t1.setDateCreated(new Date());
        t1.setMinedInBlockIndex(GENESIS_BLOCK_INDEX);
        t1.setTransferSuccessful(true);

        Transaction t2 = new Transaction();
        t2.setFromAddress(INIT_MINER_ADDRESS);
        t2.setToAddress("feff4ac90f8fcc68bfbdf882d52a806e8ac738ad");
        t2.setValue(20L);
        t2.setDateCreated(new Date());
        t2.setMinedInBlockIndex(GENESIS_BLOCK_INDEX);
        t2.setTransferSuccessful(true);

        genesis.addTransaction(faucetTransacton);
        genesis.addTransaction(t1);
        genesis.addTransaction(t2);
        genesis.setDateCreated(new Date());
        genesis.setDifficulty(0);
        genesis.setMinedBy(INIT_MINER_ADDRESS);
        genesis.setPreviousBlockHash(null);
        genesis.setNonce(0L);
        genesis.setBlockDataHash(hashText(genesis.getIndex() +
                collectionToJSON(genesis.getTransactions()) +
                genesis.getDifficulty() +
                genesis.getPreviousBlockHash() +
                genesis.getMinedBy()));
        genesis.setBlockHash(hashText(genesis.getBlockDataHash() +
                genesis.getDateCreated() +
                genesis.getDifficulty()));

        return genesis;
    }

    @Bean
    public NodeInfoViewModel generateNodeInfoViewModel() {
        NodeInfoViewModel nodeInfoViewModel = new NodeInfoViewModel();
        nodeInfoViewModel.setAbout(nodeAbout);
        nodeInfoViewModel.setNodeName(nodeName);

        //TODO: properties are not set difficulty, etc.
        //TODO: call /info

        return nodeInfoViewModel;
    }

    //TODO: adjust difficulty
}
