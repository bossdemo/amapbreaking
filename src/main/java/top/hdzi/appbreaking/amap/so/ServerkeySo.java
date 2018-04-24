package top.hdzi.appbreaking.amap.so;

import top.hdzi.appbreaking.utils.Base64Utils;
import top.hdzi.appbreaking.utils.XXTeaUtils;

/**
 * Created by taojinhou on 2018/4/20.
 */
public class ServerkeySo {
    private static byte[] key = "LXlvWaosMcJCJwVn1".getBytes();

    public static String amapDecode(String enc) {
        return new String(XXTeaUtils.decrypt(Base64Utils.decode2byte(enc), key, false, false, false));
    }

    public static String amapEncode(String dec) {
        return Base64Utils.encode(XXTeaUtils.encrypt(dec.getBytes(), key, false, false, false));
    }

    public static String getAosChannel() {
        return "amap7a";
    }

    public static String getAosKey() {
        return "xnaEwInMxaMQ2m0cw6Y1bDm7ns0YVxYS9v7JlC8I";
    }
}
