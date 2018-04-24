package top.hdzi.appbreaking.amap;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import top.hdzi.appbreaking.amap.so.ServerkeySo;
import top.hdzi.appbreaking.utils.HttpClientUtils;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class AmapApi {
    /**
     * 高德地图搜索列表
     * @param pos
     * @param keyword
     * @param locale
     * @return
     */
    public static Map searchList(String[] pos, String keyword, String[] locale) {
        var url = "http://m5.amap.com/ws/mapapi/poi/infolite/";
        var params = new HashMap<String, String>();
        params.put("ent", ParamsUtils.getEnt());
        var in = ParamsUtils.getSearchListIn(pos, keyword, locale);
        //System.out.println(in);
        params.put("in", URLEncoder.encode(ServerkeySo.amapEncode(in)));
        params.put("csid", ParamsUtils.getCsid());
        url = HttpClientUtils.spliceUrl(url, params);
        //System.out.println(url);
        var mapper = new ObjectMapper();
        try {
            return mapper.readValue(HttpClientUtils.get(url), Map.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 详情页
     * @param id
     * @return
     */
    public static Map searchDeep(String id) {
        var url = "http://m5.amap.com/ws/shield/dsp/valueadded/deepinfo/poilite";
        var params = new HashMap<String, String>();
        params.put("ent", ParamsUtils.getEnt());
        var in = ParamsUtils.getDeepIn();
        //System.out.println(in);
        params.put("in", URLEncoder.encode(ServerkeySo.amapEncode(in)));
        params.put("csid", ParamsUtils.getCsid());
        url = HttpClientUtils.spliceUrl(url, params);
        var deepBody = ServerkeySo.amapEncode(ParamsUtils.getDeepBody(id));
        //System.out.println(url);
        var mapper = new ObjectMapper();
        try {
            return mapper.readValue(HttpClientUtils.post(url, deepBody), Map.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 搜索结果+详情页(details字段)
     * @param pos
     * @param keyword
     * @param locale
     * @return
     */
    public static List search(String[] pos, String keyword, String[] locale) {
        List bases = (List) searchList(pos, keyword, locale).get("poi_list");
        Iterator iterator = bases.iterator();
        while (iterator.hasNext()) {
            Map map = (Map) iterator.next();
            var id = (String) map.get("id");
            if (StringUtils.isNotEmpty(id)) {
                map.put("details", AmapApi.searchDeep(id).get("poiinfo"));
            } else {
                iterator.remove();
            }
        }

        return bases;
    }
}
