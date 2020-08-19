package com.mesalab.ua;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;
import com.mesalab.ua.dto.UserAgent;

import java.io.*;
import java.util.*;

public class Test {
    @org.junit.Test
    public void uaTest() throws IOException {

        String uaString = "Mozilla/5.0 (iPhone; CPU iPhone OS 5_1_1 like Mac OS X) AppleWebKit/534.46 (KHTML, like Gecko) Version/5.1 Mobile/9B206 Safari/7534.48.3";

        Parser uaParser = new Parser();
        UserAgent c = uaParser.parse(uaString);
        System.err.println(c);
        System.out.println(c.client.family); // => "Mobile Safari"
        System.out.println(c.client.major);  // => "5"
        System.out.println(c.client.minor);  // => "1"

        System.out.println(c.os.family);        // => "iOS"
        System.out.println(c.os.major);         // => "5"
        System.out.println(c.os.minor);         // => "1"

        System.out.println(c.device.family);    // => "iPhone"
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        String uaString = "Mozilla/6.0 (iPhone; CPU iPhone OS 5_1_1 like Mac OS X) AppleWebKit/534.46 (KHTML, like Gecko) Version/5.1 Mobile/9B206 Safari/7534.48.3";
//        String usa = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.79 Safari/537.36";
        Parser uaParser = new Parser();
        UserAgent parse1 = uaParser.parse(uaString);
//        System.err.println(JSON.toJSONString(parse, true));
        //创建请求头
        LinkedMultiValueMap requestMap = new LinkedMultiValueMap();
        HttpEntity httpEntity = new HttpEntity(requestMap, null);
        RestTemplate restTemplate = new RestTemplate();

        //构建参数
        Map<String, Object> deleteParamMap = getDeleteSource();
        String url = "http://" + "192.168.40.193:8123";
        deleteParamMap.put("query", "SELECT DISTINCT (user_agent) FROM k18_ods.tbs_ods_ntc_http_log_local where notEmpty(user_agent) LIMIT 100 FORMAT JSON;");
        // 发送请求解析结果
        String result = restTemplate.postForObject(getUrlWithParams(url, deleteParamMap), httpEntity, String.class);

        Map mapObj = JSONObject.parseObject(result, Map.class);
        List<String> uaList = new ArrayList();
        List<Map> mapList = (List<Map>) mapObj.get("data");
        for (Map m : mapList) {
            uaList.addAll(m.values());
        }
        Collections.sort(uaList);
        String filePath = "C:\\Users\\Administrator\\Desktop\\data\\2.2.0\\uap.txt";
        //输出到文件
        try(BufferedWriter fos = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath)))){
            for (String ua : uaList) {
                Parser parser = new Parser();
                UserAgent parse = parser.parse(ua);
                System.err.println(JSON.toJSONString( parser.parse(ua), true));
                fos.write(JSON.toJSONString( parser.parse(ua)));
                fos.newLine();
            }
        };


    }


    /**
     * 数据库连接参数
     *
     * @return map
     * {database：,
     * password：,
     * user:,
     * }
     */
    private static Map<String, Object> getDeleteSource() {
        //删除sql参数拼接
        Map<String, Object> deleteParamMap = new HashMap();
        deleteParamMap.put("database", "k18_ods");
        deleteParamMap.put("password", "111111");
        deleteParamMap.put("user", "default");
        return deleteParamMap;
    }

    /**
     * 拼装url
     * url ,参数map
     */
    public static String getUrlWithParams(String url, Map<String, Object> params) {
        try {
            StringBuffer query = new StringBuffer();
            for (Map.Entry<String, Object> kv : params.entrySet()) {
                query.append(kv.getKey() + "=");
                query.append(kv.getValue() + "&");
            }
            if (query.lastIndexOf("&") > 0) {
                query.deleteCharAt(query.length() - 1);
            }
            return url + "?" + query.toString();
        } catch (Exception e) {
            throw e;
        }
    }
}
