package top.hdzi.appbreaking.amap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.util.List;
import java.util.Map;

public class AmapApiTest {
    public String[] pos = new String[]{"121.6288085281849", "31.20488812869815"};

    @Test
    public void search() throws JsonProcessingException {
        List list = AmapApi.search(pos, "美食", new String[]{
                "121.6288085281849",
                "31.20488812869815",
                "121.6288085281849",
                "31.20488812869815"
        });
        System.out.println(new ObjectMapper().writeValueAsString(list));
    }

    @Test
    public void searchList() throws JsonProcessingException {
        Map map = AmapApi.searchList(pos, "美食", new String[]{
                "100.62316918373105",
                "31.213122611525563",
                "100.63444653153421",
                "31.196644898732842"
        });
        System.out.println(new ObjectMapper().writeValueAsString(map));
    }

    @Test
    public void searchDeep() throws JsonProcessingException {
        System.out.println(new ObjectMapper().writeValueAsString(AmapApi.searchDeep("B0FFHEZHEG")));
    }
}