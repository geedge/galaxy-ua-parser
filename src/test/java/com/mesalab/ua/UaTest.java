package com.mesalab.ua;

import com.mesalab.ua.dto.UserAgent;
import eu.bitwalker.useragentutils.OperatingSystem;
import nl.basjes.parse.useragent.UserAgentAnalyzer;
import org.junit.Test;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UaTest {

    public static void main(String[] args) {
        String uaString = "User-Agent: Mozilla/5.0 (Linux; U; android 2.3.7; en-us; Nexus One Build/FRF91) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1";
//        String uaString = "Dalvik/2.1.0 (Linux; U; Android 9; EML-AL00 Build/HUAWEIEML-AL00)";
        try {
            Parser uaParser = new Parser();
            UserAgent c = uaParser.parse(uaString);
//            System.err.println(c);
            System.out.println("=============================================");
            System.out.println("=============================================");

//            eu.bitwalker.useragentutils.UserAgent userAgent = eu.bitwalker.useragentutils.UserAgent.parseUserAgentString(uaString);
            OperatingSystem operatingSystem = OperatingSystem.parseUserAgentString(uaString);
//            System.err.println(JSON.toJSONString(browser));
            System.err.println(operatingSystem);

            System.out.println("=============================================");

            UserAgentAnalyzer uaa = UserAgentAnalyzer
                    .newBuilder()
                    .hideMatcherLoadStats()
                    .withCache(10000)
                    .build();

            nl.basjes.parse.useragent.UserAgent.ImmutableUserAgent agent = uaa.parse(uaString);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @Test
    public void test(){
        String agentString = "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.2; WOW32; Trident/7.0; .NET4.0C; .NET4.0E; .NET CLR 2.0.50727; .NET CLR 3.0.30729; .NET CLR 3.5.30729; InfoPath.3)";
        Pattern pattern = Pattern.compile("Win(?:dows NT |32NT\\/)6\\.2");
        Matcher matcher = pattern.matcher(agentString);
        System.out.println(matcher.find());

    }

}
