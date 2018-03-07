package com.topchain.node.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.Enumeration;

/**
 * Created by eslavov on 13-Feb-18.
 */
@Component
public class NodeUtils {
    public static String SERVER_PORT;
    public static long BLOCK_REWARD_COINS;
    public static final String NIL_ADDRESS = "0000000000000000000000000000000000000000";


    @Value("${server.port}")
    public void setServerPort(String port) {
        SERVER_PORT = port;
    }

    @Value("${block.rewardCoins}")
    public void setBlockRewardCoins(long rewardCoins) {
        BLOCK_REWARD_COINS = rewardCoins;
    }

    /**
     * Hashes input text to hex value, currently using SHA-256
     *
     * @param text message to hash
     * @return hash in hex value
     */
    public static String hashText(String text) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        messageDigest.update(text.getBytes(StandardCharsets.UTF_8));
        byte[] digest = messageDigest.digest();
        String hex = String.format("%064x", new BigInteger(1, digest));

        return hex;
    }

//    public static boolean checkSignature(String message, String[] signature, String publicKey) {
//        Signature ecdsaVerify = null;
//        try {
//            ecdsaVerify = Signature.getInstance("SHA256withECDSA", "BC");
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        } catch (NoSuchProviderException e) {
//            e.printStackTrace();
//        }
//
//        PublicKey publicKey1 = PublicKey.
//        ecdsaVerify.initVerify(publicKey);
//        ecdsaVerify.update(plaintext.getBytes("UTF-8"));
//        boolean result = ecdsaVerify.verify(signature);
//
//        return false;
//    }
//
//    public static PublicKey getKey(String key){
//        try{
//            byte[] byteKey = Base64.decode(key.getBytes(), Base64.DEFAULT);
//            X509EncodedKeySpec X509publicKey = new X509EncodedKeySpec(byteKey);
//            KeyFactory kf = KeyFactory.getInstance("RSA");
//
//            return kf.generatePublic(X509publicKey);
//        }
//        catch(Exception e){
//            e.printStackTrace();
//        }
//
//        return null;
//    }

    public static String collectionToJSON(Collection collection) {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        final ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(out, collection);
        } catch (IOException e) {
            e.printStackTrace();
        }

        final byte[] data = out.toByteArray();

        return new String(data);
    }

    public static String serializeJSON(Object obj, boolean pretty) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        String resultJSON = null;
        try {
            if (pretty) {
                resultJSON = mapper.writerWithDefaultPrettyPrinter()
                        .writeValueAsString(obj);
            } else {
                resultJSON = mapper.writeValueAsString(obj);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return resultJSON;
    }

    public static String getServerURL() {
        String ipAddress = null;
        Enumeration en = null;
        try {
            en = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e) {
            e.printStackTrace();
        }

        while(en.hasMoreElements()){
            NetworkInterface ni=(NetworkInterface) en.nextElement();
            Enumeration ee = ni.getInetAddresses();
            while(ee.hasMoreElements()) {
                InetAddress ia = (InetAddress) ee.nextElement();
                //TODO: find better way to get address
                if (ia.getHostAddress().startsWith("192")) {
                    ipAddress = ia.getHostAddress();
                }
            }
        }

        return "http://" +
                    ipAddress + ":" +
                    SERVER_PORT;
    }

    public static String newString(String append, int length) {
        StringBuffer outputBuffer = new StringBuffer(length);
        for (int i = 0; i < length; i++) {
            outputBuffer.append(append);
        }

        return outputBuffer.toString();
    }

    public static long milCoinsFromCoins(long coins) {
        return coins * 1000;
    }

    public static long micCoinsFromCoins(long coins) {
        return milCoinsFromCoins(coins) * 1000;
    }

    public static long coinsFromMilCoins(long milCoins) {
        return milCoins / 1000;
    }

    public static long coinsFromMicCoins(long micCoins) {
        return coinsFromMilCoins(micCoins / 1000);
    }
}
