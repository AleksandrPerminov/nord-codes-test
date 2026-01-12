package utils;

import java.security.SecureRandom;

public class TokenUtil {

    private static final String HEX_CHARS = "0123456789ABCDEF";
    private static final SecureRandom random = new SecureRandom();

    public static String generateUniqueToken() {
        StringBuilder sb = new StringBuilder(32);
        for (int i = 0; i < 32; i++) {
            sb.append(HEX_CHARS.charAt(random.nextInt(HEX_CHARS.length())));
        }
        return sb.toString();
    }
}