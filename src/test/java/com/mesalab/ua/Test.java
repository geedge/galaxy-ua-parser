package com.mesalab.ua;

import com.alibaba.fastjson.JSONObject;
import com.mesalab.ua.dto.UserAgent;
import org.springframework.http.HttpEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.StopWatch;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.*;

public class Test {
    @org.junit.Test
    public void uaTest() throws IOException {

        String uaString = "Mozilla/5.0 (iPhone; CPU iPhone OS 5_1_1 like Mac OS X) AppleWebKit/534.46 (KHTML, like Gecko) Version/5.1 Mobile/9B206 Safari/7534.48.3";

        Parser uaParser = new Parser();
        UserAgent c = uaParser.parse(uaString);
        System.err.println(c);
        System.out.println(c.client.name); // => "Mobile Safari"
        System.out.println(c.client.version);  // => "5"
        System.out.println(c.client.type);  // => "1"

        System.out.println(c.os.name);        // => "iOS"
        System.out.println(c.os.version);         // => "5"
        System.out.println(c.os.platform);         // => "1"

        System.out.println(c.device);    // => "iPhone"
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
        deleteParamMap.put("query", "SELECT DISTINCT (user_agent) FROM k18_ods.tbs_ods_ntc_http_log_local where notEmpty(user_agent) LIMIT 100000 FORMAT JSON;");
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

//        UserAgentAnalyzer uaa = UserAgentAnalyzer
//                .newBuilder()
//                .hideMatcherLoadStats()
//                .withCache(10000)
//                .build();
        //输出到文件
        try(BufferedWriter fos = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath)))){
            Parser parser = new Parser();
            double num =0;
            double num2 =0;
            StopWatch sw = new StopWatch();
            sw.start();
            System.err.println("***********开始解析***********");
             for ( String ua : uaList) {

                UserAgent parse = parser.parse(ua);
//                eu.bitwalker.useragentutils.UserAgent userAgent = eu.bitwalker.useragentutils.UserAgent.parseUserAgentString(ua);
//                Browser browser = Browser.parseUserAgentString(ua);
//                System.err.println(JSON.toJSONString(operatingSystem));
//                System.err.println(JSON.toJSONString(userAgent));
//                System.err.println(JSON.toJSONString(browser));
//                nl.basjes.parse.useragent.UserAgent.ImmutableUserAgent agent = uaa.parse(ua);
//                System.err.println(ua);
//                    System.err.println("\n"+num+": "+"\n"+JSON.toJSONString( parse.os ,true)+ "\n"+"DeviceClass:"+agent);
                 if ("Other".equals(parse.os.name)||"Other".equals(parse.os.version)||"Other".equals(parse.os.platform)){
                     num++;
                 }

                if ("Other".equals(parse.device.name)||"Other".equals(parse.device.type)||"Other".equals(parse.device.brand)||"Other".equals(parse.device.model)){
                    num2++;
                }
//                fos.write(JSON.toJSONString( parser.parse(ua)));
//                fos.newLine();
            }
            sw.stop();
            System.out.println("解析ua 用时："+ sw.getTotalTimeSeconds()+" S");
            System.out.println("总共ua os条数："+ uaList.size()+", 未解出条数："+num+" , 解析率为："+String.format("%.2f %%",((uaList.size()-num)/uaList.size())*100));
            System.out.println("总共ua device条数："+ uaList.size()+", 未解出条数："+num2+" , 解析率为："+String.format("%.2f %%",(((uaList.size()-num2)/uaList.size())*100)));
            System.err.println("***********结束解析***********");
        }


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
