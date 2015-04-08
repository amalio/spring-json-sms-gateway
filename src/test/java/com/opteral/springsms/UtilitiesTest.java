package com.opteral.springsms;

import com.opteral.springsms.config.ConfigValues;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class UtilitiesTest {


    @Test
    public  void testGetConfig() throws IOException {

        ConfigValues.MAX_SMS_SIZE = 0;
        ConfigValues.SUBID_MAX_SIZE = 0;
        ConfigValues.SENDER_MAX_SIZE = 0;

        Utilities.getConfig(true);

        assertEquals("87.222.103.149", ConfigValues.SMSC_IP);
        assertEquals("pavel", ConfigValues.SMSC_USERNAME);
        assertEquals("wpsd", ConfigValues.SMSC_PASSWORD);
        assertEquals(8056, ConfigValues.SMSC_PORT);

        assertEquals(140, ConfigValues.MAX_SMS_SIZE);
        assertEquals(11, ConfigValues.SENDER_MAX_SIZE);
        assertEquals(20, ConfigValues.SUBID_MAX_SIZE);
        assertEquals("jjsgDS", ConfigValues.DATASOURCE);
    }
}
