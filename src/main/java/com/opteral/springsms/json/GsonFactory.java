package com.opteral.springsms.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonFactory {

    private GsonFactory(){

    }


    public static Gson getGson() {

        GsonBuilder gsonBuilder = new GsonBuilder();

        return gsonBuilder.setDateFormat("yyyy-MM-dd HH:mm").create();
    }
}
