package ru.codes.nord.utils;

public class MaskUtil {

    public static String maskToken(String uniqueToken) {
        return uniqueToken.substring(0, 3) + "******" + uniqueToken.substring(uniqueToken.length() - 3);
    }
}
