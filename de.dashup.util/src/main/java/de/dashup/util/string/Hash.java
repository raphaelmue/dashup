package de.dashup.util.string;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hash {

    private final static String HASH_ALGORITHM = "SHA-256";

    public static String create(String plainText, String salt) {
        return create(plainText + salt);
    }

    static String create(String plainText) {
        try {
            final MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
            digest.reset();
            return binToHex(digest.digest(plainText.getBytes())).toLowerCase();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String binToHex(byte[] data) {
        return String.format("%0" + (data.length*2) + "X", new BigInteger(1, data));
    }
}

