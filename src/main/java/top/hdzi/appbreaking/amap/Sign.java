package top.hdzi.appbreaking.amap;

import top.hdzi.appbreaking.amap.so.ServerkeySo;
import top.hdzi.appbreaking.utils.HashUtils;

/**
 * Created by taojinhou on 2018/4/23.
 */
public class Sign {
    public static String getSign(String text) {
        return HashUtils.getMD5(ServerkeySo.getAosChannel() + text + "@" + ServerkeySo.getAosKey()).toUpperCase();
    }
}
