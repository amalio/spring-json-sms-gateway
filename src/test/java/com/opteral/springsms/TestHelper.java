package com.opteral.springsms;

public class TestHelper {

    public static int EXCEPTION_EXPECTED = 1;
    public static int NORMAL = 0;




    public static String genString(int num) {

        char aChar = 'a';
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < num; i++) {
            sb.append(aChar);
        }
        return sb.toString();
    }
}
