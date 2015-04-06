package com.opteral.springsms;

import com.opteral.springsms.config.ConfigProvider;
import com.opteral.springsms.config.ConfigValues;
import com.opteral.springsms.config.RootConfig;
import com.opteral.springsms.web.WebConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ConfigProvider.class)
@ActiveProfiles("test")
public class UtilitiesTest {

    @Autowired
    @Qualifier("config_input")
    InputStream inputConfig;

    @Test
    public  void testGetConfig() throws IOException {

        ConfigValues.MAX_SMS_SIZE = 0;
        ConfigValues.SUBID_MAX_SIZE = 0;
        ConfigValues.SENDER_MAX_SIZE = 0;

        Utilities.getConfig(inputConfig);

        assertEquals("87.222.103.149", ConfigValues.SMSC_IP);
        assertEquals("pavel", ConfigValues.SMSC_USERNAME);
        assertEquals("wpsd", ConfigValues.SMSC_PASSWORD);
        assertEquals(8056, ConfigValues.SMSC_PORT);

        assertEquals(140, ConfigValues.MAX_SMS_SIZE);
        assertEquals(11, ConfigValues.SENDER_MAX_SIZE);
        assertEquals(20, ConfigValues.SUBID_MAX_SIZE);
    }
}
