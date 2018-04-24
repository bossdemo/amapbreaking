package top.hdzi.appbreaking.utils;

import java.util.Base64;

/**
 * Created by taojinhou on 2018/3/19.
 */
public class Base64Utils {
    public static String decode(String code) {
        return new String(Base64.getDecoder().decode(code));
    }

    public static byte[] decode2byte(String code) {
        return Base64.getDecoder().decode(code);
    }

    public static String encode(String code) {
        return new String(Base64.getEncoder().encode(code.getBytes()));
    }

    public static String encode(byte[] code) {
        return new String(Base64.getEncoder().encode(code));
    }
}
