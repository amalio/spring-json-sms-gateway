package com.opteral.springsms;


import com.opteral.springsms.config.ConfigValues;

import javax.ws.rs.client.ClientBuilder;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Utilities {
    public static void getConfig(boolean isTest) throws IOException {

        Properties properties = new Properties();

        InputStream inputStream = null;
        if (isTest)
            inputStream = Utilities.class.getClassLoader().getResourceAsStream("gateway.properties");
        else
            inputStream = new FileInputStream("/etc/amalio/gateway.properties");

        properties.load(inputStream);

        ConfigValues.SMSC_IP = properties.getProperty("smsc_ip");
        ConfigValues.SMSC_PORT = Integer.parseInt(properties.getProperty("smsc_port"));
        ConfigValues.SMSC_USERNAME = properties.getProperty("smsc_username");
        ConfigValues.SMSC_PASSWORD = properties.getProperty("smsc_password");

        ConfigValues.MAX_SMS_SIZE = Integer.parseInt(properties.getProperty("sms_max_size"));
        ConfigValues.SENDER_MAX_SIZE = Integer.parseInt(properties.getProperty("sender_max_size"));
        ConfigValues.SUBID_MAX_SIZE = Integer.parseInt(properties.getProperty("subid_max_size"));

    }

    public static void sendGet(String url)  {

        ClientBuilder.newClient().target(url).request().async().get();

    }
}
