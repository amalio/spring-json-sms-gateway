package com.opteral.springsms.json;

public class OutParser {

    public static String getJSON (ResponseJSON responseJSON)  {

        try
        {
            return  GsonFactory.getGson().toJson(responseJSON);

        }
        catch (Exception e)
        {
            return "incorrect format";

        }

    }

}
