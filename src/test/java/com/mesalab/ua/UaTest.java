package com.mesalab.ua;

import com.mesalab.ua.dto.UserAgent;

import java.io.IOException;

public class UaTest {

    public static void main(String[] args) {
        String uaString = "Mozilla/5.0 (iPhone; CPU iPhone OS 5_1_1 like Mac OS X) AppleWebKit/534.46 (KHTML, like Gecko) Version/5.1 Mobile/9B206 Safari/7534.48.3";
        Parser uaParser = null;
        try {
            uaParser = new Parser();
            UserAgent c = uaParser.parse(uaString);
            System.err.println(c);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
