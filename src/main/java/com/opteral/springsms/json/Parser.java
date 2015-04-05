package com.opteral.springsms.json;

public class Parser {

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

    public static String getJSON (RequestJSON requestJSON)  {

        try
        {
            return  GsonFactory.getGson().toJson(requestJSON);

        }
        catch (Exception e)
        {
            return "incorrect format";

        }

    }

}
