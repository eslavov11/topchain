package com.topchain.node.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;
import java.math.BigInteger;
import java.util.Collection;

/**
 * Created by eslavov on 13-Feb-18.
 */
public class NodeUtils {
    /**
     * Hashes input text to hex value, currently using SHA-256
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
}
