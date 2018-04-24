package top.hdzi.appbreaking.amap;

import top.hdzi.appbreaking.utils.HttpClientUtils;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.UUID;

/**
 * Created by taojinhou on 2018/4/23.
 */
public class ParamsUtils {
    private static class ParamMap extends HashMap<String, String> {
        public String putEnc(String key, String value) {
            return super.put(key, URLEncoder.encode(value));
        }

        public String getDec(String key) {
            return URLDecoder.decode(super.get(key));
        }
    }

    public static String getSearchListIn(String[] pos, String keyword, String[] locale) {
        var params = new ParamMap();
        params.putEnc("search_operate", "1");
        params.putEnc("need_recommend", "1");
        params.putEnc("cifa", "3804800717D83F07B42EDC01010702CC0100000000000000000000103600000F000000FF310000000000001600000005004D3537314307006D32636E6F746505004D65697A750A00382E312E322E32323136000000000000000000002F00");
        params.putEnc("utd_sceneid", "101000");
        params.putEnc("transfer_pdheatmap", "0");
        params.putEnc("user_loc", String.join(",", pos));
        params.putEnc("siv", "ANDH080102");
        params.putEnc("dic", "C021100013426");
        params.putEnc("loc_strict", "false");
        params.putEnc("version", "2.19");
        params.putEnc("channel", "amap7a");
        params.putEnc("session", "230397662");
        //params.putEnc("sign", "1E564473597D609B5628E6C07E04CD82");
        params.putEnc("transfer_nearby_time_opt", "");
        params.putEnc("transfer_nearby_bucket", "");
        params.putEnc("need_utd", "true");
        params.putEnc("geoobj_adjust", "");
        params.putEnc("query_type", "TQUERY");
        params.putEnc("transfer_nearby_keyindex", "");
        params.putEnc("superid", "a_05");
        params.putEnc("need_magicbox", "true");
        params.putEnc("stepid", "49");
        params.putEnc("tid", "Vjp9ns5lmMDAEP/B0uUXfKH");
        params.putEnc("need_parkinfo", "true");
        params.putEnc("transparent", "");
        params.putEnc("transfer_filter_flag", "0");
        params.putEnc("cluster_state", "5");
        params.putEnc("scenario", "1");
        params.putEnc("aetraffic", "9");
        params.putEnc("appstartid", "230397663");
        params.putEnc("geoobj", String.join("|", locale));
        params.putEnc("outputEnc", "json");
        params.putEnc("BID_F", "M010000");
        params.putEnc("diu3", "0f85379929c44efeb897a4bd41d8cfec-11e41d7d7823e41ddd6c1447aeecf4be");
        params.putEnc("need_codepoint", "true");
        params.putEnc("spm", "15720513165958896454685448308770");
        params.putEnc("addr_poi_merge", "true");
        params.putEnc("pagesize", "10");
        params.putEnc("keywords", keyword);
        params.putEnc("dip", "10880");
        params.putEnc("diu", "99000645711814");
        params.putEnc("query_mode", "normal");
        params.putEnc("adiu", "75ad956e96004knjioier56181da4e");
        params.putEnc("diu2", "fbec258cebabffffffe8");
        params.putEnc("div", "ANDH080102");
        params.putEnc("is_classify", "true");
        params.putEnc("pagenum", "1");
        params.putEnc("citysuggestion", "true");
        params.putEnc("direct_jump", "true");
        params.putEnc("dibv", "2216");
        params.putEnc("user_city", "310000");
        params.putEnc("log_center_id", "");
        params.putEnc("client_network_class", "4");
        params.putEnc("sign", Sign.getSign(params.getDec("keywords") + params.getDec("geoobj")));

        return HttpClientUtils.spliceParams(params);
    }

    public static String getDeepIn() {
        var params = new ParamMap();

        params.putEnc("dip", "10880");
        params.putEnc("diu", "99000645711814");
        params.putEnc("sign", "15F9E1EDE38CC349B3C58AADC89C4AD5");
        params.putEnc("session", "230675426");
        params.putEnc("aetraffic", "9");
        params.putEnc("appstartid", "230675426");
        params.putEnc("adiu", "75ad956e96004knjioier56181da4e");
        params.putEnc("cifa", "3804800718EC3F0755E6DB01010702CC0100000000000000000000103600000F000000D1330000000000001600000005004D3537314307006D32636E6F746505004D65697A750A00382E312E322E32323136000000000000000000001D00");
        params.putEnc("diu2", "fbec258cebabffffffe8");
        params.putEnc("ajxversion", "v0005.0.080102.2160.0");
        params.putEnc("div", "ANDH080102");
        params.putEnc("output", "json");
        params.putEnc("siv", "ANDH080102");
        params.putEnc("dic", "C021100013426");
        params.putEnc("BID_F", "M010000");
        params.putEnc("stepid", "106");
        params.putEnc("diu3", "b1cf2715c7a145f48fb9192a1a4b7326-c32b2bf3c86e2918809bca59633d7151");
        params.putEnc("tid", "V+jp9ns5lmMDAEP/B0uUXfKH");
        params.putEnc("spm", "0725486450776563");
        params.putEnc("dibv", "2216");
        params.putEnc("client_network_class", "4");
        params.putEnc("channel", "amap7a");

        return HttpClientUtils.spliceParams(params) + new String(new byte[]{0, 3, 3, 3});
        //return ServerkeySo.amapDecode("YNS4Zmv3dfV0GBEOgeNWS5UTL4gq4uab5Apg/dcvjlNRj6LCvzqIlv62D+o/fQErWxTiPwKSeIZ04zThy4V9Y0LRIO/9HnuxbTRXNK43wPmremWsMtFnKmco0DhhPL5WhUYyCoxR9zZsZSeicUPatSK5uLK5tAIgmnJg+ikEFiJfQ0XbyvPNDXe+4QDEydanJKOkojb7A7XCjxncMpHk6lZr49tKBUrOs40BTHkJj1+whxh9KXp2D0RqhsTuccBlS+LD/jmONj4mpVLN4qXRiEerTazP1stKgVNFEKYb/ChXRQub0NHKcYl5ABjr6PipY6EL9bea6ISsSnA3X43QDC952+bo06sXfGCd7dreB3dn3unot7XPZzmH6vCorUNxMnUy/BQscdhs1bqptLfqOpcT4Tt6rfmP71Xf8YWHZf1eg/xAFfE1Vwt/75bC8vBx3tzlLwCVwXrjT+9Axdm9YO2OQIwyO5zpmqISlNnsdMVwzFjmUsD2YBhhHc3AnzZ5QVmLc6wcU6qGt+7Qppo3Bq9RRYTNtff/UUygxjDaHaXgLSQRbyjnLgqZdVlJQmDmSiAz2ob/9zvbtLRCKlC9GynuxatmXbV+Qp8PdlVQLutLxUwKeaSbKOr/ssN0fNgiVFTYGIjDt3gcS2slDOL3a8wJwOQgcvn+t4bBoq+Hf0vruVBANneuNqDBIG2uzeT5TdSkKKGoDr75HsyKhYq+dl+fplfZe4d3/FxZmyzqsg8/8cUNG8Od3HhdsEhrUfjhFAK7yiObH+38eENVx06Bx6tKRpia9e5lZ1sO3ZBwob5HphlpXoS/gr+CbHOmZQwZn9se5HufEV9jCEvLsXvSeJWKjK6V7VZvoiJo5fdH23n5ejtvtrURKjbBeuI=");
    }

    public static String getDeepBody(String id) {
        var params = new ParamMap();
        params.putEnc("user_loc", "121.629027,31.204895"); // 这个位置干什么用,代考???
        params.putEnc("src", "poitip");
        params.putEnc("deepcount", "1");
        params.putEnc("mode", "511");
        params.putEnc("user_adcode", "310000");
        params.putEnc("poiid", id);
        params.putEnc("typecode", "050100");
        params.putEnc("ajx_page_verison", "");
        params.putEnc("ver", "3");

        return HttpClientUtils.spliceParams(params) + new String(new byte[]{0, 4, 4, 4, 4});
        //return ServerkeySo.amapDecode("KY+7I4ijxiNr7+MUaJZ2r2cfhciZVxrMCrwbp447a5NTywGTzKIyBT2vs7wHa8rt4fdhUVUMT9rNQCb1eCB+vmloCeG8WLp7xckUo+yHUvxIxTaYmmY0U5GRSWDP9vC747Xz/6GVCcW5NKqqg5a+ZBARST57MRpI+ZqWqSSDBqG/6xB0f57hLX54yBLf01Lf");
    }

    public static String getEnt() {
        return "2";
    }

    public static String getCsid() {
        return UUID.randomUUID().toString();
    }
}
