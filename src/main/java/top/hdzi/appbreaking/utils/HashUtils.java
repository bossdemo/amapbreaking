package top.hdzi.appbreaking.utils;

import java.security.MessageDigest;

public class HashUtils {

    // 全局数组
    private final static String[] STR_DIGITS = {"0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    public static String getMD5(String message) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] bytes = md.digest(message.getBytes("utf-8"));
            return toHex(bytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String toHex(byte[] bytes) {
        StringBuilder ret = new StringBuilder(bytes.length * 2);
        for (byte aByte : bytes) {
            ret.append(STR_DIGITS[(aByte >> 4) & 0x0f]);
            ret.append(STR_DIGITS[aByte & 0x0f]);
        }
        return ret.toString();
    }
}