package com.topchain.node.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;
import java.math.BigInteger;

/**
 * Created by eslavov on 13-Feb-18.
 */
public class NodeUtils {
    public static String hashSHA256(String message) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        messageDigest.update(message.getBytes(StandardCharsets.UTF_8));
        byte[] digest = messageDigest.digest();
        String hex = String.format("%064x", new BigInteger(1, digest));

        return hex;
    }
}
