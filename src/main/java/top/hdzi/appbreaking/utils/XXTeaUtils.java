package top.hdzi.appbreaking.utils;

/**
 * Created by taojinhou on 2018/4/4.
 */
public class XXTeaUtils {
    /**
     * Encrypt data with key.
     *
     * @param data
     * @param key
     * @return
     */
    public static byte[] encrypt(byte[] data, byte[] key, boolean dataInclude, boolean keyInclude, boolean encryptInclude) {
        if (data.length == 0) {
            return data;
        }
        return toByteArray(
                encrypt(toIntArray(data, dataInclude), toIntArray(key, keyInclude)), encryptInclude);
    }

    /**
     * Decrypt data with key.
     *
     * @param data
     * @param key
     * @return
     */
    public static byte[] decrypt(byte[] data, byte[] key, boolean dataInclude, boolean keyInclude, boolean decryptInclude) {
        if (data.length == 0) {
            return data;
        }
        return toByteArray(
                decrypt(toIntArray(data, dataInclude), toIntArray(key, keyInclude)), decryptInclude);
    }

    /**
     * Encrypt data with key.
     *
     * @param dec
     * @param oneKey
     * @return
     */
    private static int[] encrypt(int[] dec, int[] oneKey) {
        int[] key = oneKey;
        if (oneKey.length < 4) {
            key = new int[4];
            System.arraycopy(oneKey, 0, key, 0, oneKey.length);
        }
        int n = dec.length;
        int y;
        int p;
        int rounds = 6 + 52 / n;
        int sum = 0;
        int z = dec[n - 1];
        int delta = 0x9E3779B9;
        do {
            sum += delta;
            int e = (sum >>> 2) & 3;
            for (p = 0; p < n - 1; p++) {
                y = dec[p + 1];
                z = dec[p] += (z >>> 5 ^ y << 2) + (y >>> 3 ^ z << 4) ^ (sum ^ y) + (key[p & 3 ^ e] ^ z);
            }
            y = dec[0];
            z = dec[n - 1] += (z >>> 5 ^ y << 2) + (y >>> 3 ^ z << 4) ^ (sum ^ y) + (key[p & 3 ^ e] ^ z);
        } while (--rounds > 0);

        return dec;
    }

    /**
     * Decrypt data with key.
     *
     * @param enc
     * @param oneKey
     * @return
     */
    private static int[] decrypt(int[] enc, int[] oneKey) {
        int[] key = oneKey;
        if (oneKey.length < 4) {
            key = new int[4];
            System.arraycopy(oneKey, 0, key, 0, oneKey.length);
        }
        int n = enc.length;
        int z = enc[n - 1], y = enc[0], delta = 0x9E3779B9, sum, e;
        int p;
        int rounds = 6 + 52 / n;
        sum = rounds * delta;
        y = enc[0];
        do {
            e = (sum >>> 2) & 3;
            for (p = n - 1; p > 0; p--) {
                z = enc[p - 1];
                y = enc[p] -= (z >>> 5 ^ y << 2) + (y >>> 3 ^ z << 4) ^ (sum ^ y) + (key[p & 3 ^ e] ^ z);
            }
            z = enc[n - 1];
            y = enc[0] -= (z >>> 5 ^ y << 2) + (y >>> 3 ^ z << 4) ^ (sum ^ y) + (key[p & 3 ^ e] ^ z);
        } while ((sum -= delta) != 0);
        return enc;
    }

    /**
     * Convert byte array to int array.
     *
     * @param data
     * @param includeLength
     * @return
     */
    private static int[] toIntArray(byte[] data, boolean includeLength) {
        int n = (((data.length & 3) == 0)
                ? (data.length >>> 2)
                : ((data.length >>> 2) + 1));
        int[] result;

        if (includeLength) {
            result = new int[n + 1];
            result[n] = data.length;
        } else {
            result = new int[n];
        }
        n = data.length;
        for (int i = 0; i < n; i++) {
            result[i >>> 2] |= (0x000000ff & data[i]) << ((i & 3) << 3);
        }
        return result;
    }

    /**
     * Convert int array to byte array.
     *
     * @param data
     * @param includeLength
     * @return
     */
    private static byte[] toByteArray(int[] data, boolean includeLength) {
        int n = data.length << 2;

        ;
        if (includeLength) {
            int m = data[data.length - 1];

            if (m > n) {
                return null;
            } else {
                n = m;
            }
        }
        byte[] result = new byte[n];

        for (int i = 0; i < n; i++) {
            result[i] = (byte) ((data[i >>> 2] >>> ((i & 3) << 3)) & 0xff);
        }
        return result;
    }
}
