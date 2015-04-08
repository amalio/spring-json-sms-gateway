package com.opteral.springsms.json;

public class Parser {

    public static String getJSON (Object object)  {

        try
        {
            return  GsonFactory.getGson().toJson(object);

        }
        catch (Exception e)
        {
            return "incorrect format";

        }

    }

}
