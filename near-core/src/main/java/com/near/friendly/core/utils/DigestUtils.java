package com.near.friendly.core.utils;

import org.springframework.security.crypto.codec.Base64;

import java.security.SecureRandom;

/**
 * Util class with static methods that helps generate and encode chars.
 */
public class DigestUtils {

    private static final SecureRandom random = new SecureRandom();

    private DigestUtils() {
        /*singleton*/
    }

    /**
     * Generate random string with a max length
     *
     * @param length the size of the generated string
     * @return a random string
     */
    public static String generate(int length) {
        byte[] newSeries = new byte[length];
        random.nextBytes(newSeries);
        return new String(Base64.encode(newSeries));
    }
}
