package com.topchain.node.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.topchain.node.entity.Peer;
import com.topchain.node.model.bindingModel.NotifyBlockModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.xml.bind.DatatypeConverter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;

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

        String hex = DatatypeConverter.printHexBinary(digest);

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
        String ipAddress = null;
        Enumeration en = null;
        try {
            en = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e) {
            e.printStackTrace();
        }

        while (en.hasMoreElements()) {
            NetworkInterface ni = (NetworkInterface) en.nextElement();
            Enumeration ee = ni.getInetAddresses();
            while (ee.hasMoreElements()) {
                InetAddress ia = (InetAddress) ee.nextElement();
                // find better way to get address
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

    public static void notifyPeersForNewBlock(NotifyBlockModel notifyBlockModel, List<Peer> peers) {
        Peer currentNode = new Peer();
        currentNode.setUrl(getServerURL());
        notifyBlockModel.setPeer(currentNode);

        // Notify all peers about new transaction
        peers.forEach(p ->
                new Thread(new BlockNotifyPeersRunnable(notifyBlockModel, p))
                        .start());
    }

    private static class BlockNotifyPeersRunnable implements Runnable {
        private NotifyBlockModel notifyBlockModel;
        private Peer peer;

        public BlockNotifyPeersRunnable(NotifyBlockModel notifyBlockModel, Peer peer) {
            this.notifyBlockModel = notifyBlockModel;
            this.peer = peer;
        }

        public void run() {
            notifyNewBlockToPeer(this.notifyBlockModel, this.peer);
        }
    }

    private static void notifyNewBlockToPeer(NotifyBlockModel notifyBlockModel,
                                      Peer peer) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(
                serializeJSON(notifyBlockModel, false), httpHeaders);

        ResponseEntity<String> response = new RestTemplate()
                .postForEntity(peer.getUrl() + "/blocks/notify",
                        request, String.class);
    }
}
