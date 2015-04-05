package com.opteral.springsms;

import com.opteral.springsms.json.Parser;
import com.opteral.springsms.json.RequestJSON;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.nio.charset.Charset;

public class TestHelper {

    public static int EXCEPTION_EXPECTED = 1;
    public static int NORMAL = 0;
    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));



    public static String genString(int num) {

        char aChar = 'a';
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < num; i++) {
            sb.append(aChar);
        }
        return sb.toString();
    }



    public static byte[] convertRequestJSONtoBytes(RequestJSON requestJSON) throws IOException {
        return Parser.getJSON(requestJSON).getBytes("UTF-8");

    }
}
