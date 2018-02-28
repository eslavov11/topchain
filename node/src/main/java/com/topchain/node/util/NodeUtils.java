package com.topchain.node.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Value;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;

/**
 * Created by eslavov on 13-Feb-18.
 */
public class NodeUtils {
    public static String SERVER_PORT;

    @Value("${mongodb.db}")
    public void setServerPort(String port) {
        SERVER_PORT = port;
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
        String url = null;
        try {
            url = "http://" +
                    InetAddress.getLocalHost().getHostAddress() + ":" +
                    SERVER_PORT;
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        return url;
    }
}
