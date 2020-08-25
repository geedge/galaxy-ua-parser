package com.mesalab.ua;

import com.alibaba.fastjson.JSON;
import com.mesalab.ua.dto.UserAgent;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import nl.basjes.parse.useragent.UserAgentAnalyzer;

import java.io.IOException;

public class UaTest {

    public static void main(String[] args) {
        String uaString = "User-Agent: Mozilla/5.0 (Linux; U; android 2.3.7; en-us; Nexus One Build/FRF91) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1";
//        String uaString = "Dalvik/2.1.0 (Linux; U; Android 9; EML-AL00 Build/HUAWEIEML-AL00)";
        try {
            Parser uaParser = new Parser();
            UserAgent c = uaParser.parse(uaString);
            System.err.println(c.client);
            System.out.println("=============================================");
            System.out.println("=============================================");

            eu.bitwalker.useragentutils.UserAgent userAgent = eu.bitwalker.useragentutils.UserAgent.parseUserAgentString(uaString);
            OperatingSystem operatingSystem = OperatingSystem.parseUserAgentString(uaString);
            Browser browser = Browser.parseUserAgentString(uaString);
            System.err.println(JSON.toJSONString(browser));
            System.err.println(JSON.toJSONString(userAgent));

            System.out.println("=============================================");

            UserAgentAnalyzer uaa = UserAgentAnalyzer
                    .newBuilder()
                    .hideMatcherLoadStats()
                    .withCache(10000)
                    .build();

            nl.basjes.parse.useragent.UserAgent.ImmutableUserAgent agent = uaa.parse(uaString);
            System.err.println(agent);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
