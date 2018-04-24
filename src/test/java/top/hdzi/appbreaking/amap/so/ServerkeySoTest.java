package top.hdzi.appbreaking.amap.so;

import org.junit.Test;

public class ServerkeySoTest {

    @Test
    public void amapDecode() {
        var encode = "KY+7I4ijxiNr7+MUaJZ2r2cfhciZVxrMCrwbp447a5NTywGTzKIyBT2vs7wHa8rt4fdhUVUMT9rNQCb1eCB+vmloCeG8WLp7xckUo+yHUvxIxTaYmmY0U5GRSWDP9vC747Xz/6GVCcW5NKqqg5a+ZBARST57MRpI+ZqWqSSDBqG/6xB0f57hLX54yBLf01Lf";
        String decode = ServerkeySo.amapDecode(encode);
        System.out.println(decode);
    }
}