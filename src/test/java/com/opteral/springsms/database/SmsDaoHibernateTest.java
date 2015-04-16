package com.opteral.springsms.database;

import com.opteral.springsms.config.RootConfig;
import com.opteral.springsms.exceptions.GatewayException;
import com.opteral.springsms.model.SMS;
import com.opteral.springsms.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {HibernateTestConfig.class, RootConfig.class})
@ActiveProfiles("test")
public class SmsDaoHibernateTest {

    @Autowired
    SmsDaoHibernate smsDaoHibernate;

    @Test
    @Transactional
    public void getSMSTest() throws GatewayException {

        SMS sms = smsDaoHibernate.getSMS(EntitiesHelper.SMS_ID);

        assertNotNull(sms);
        assertEquals(EntitiesHelper.SMS_ID, sms.getId());
        assertEquals(EntitiesHelper.USER_ID, sms.getUser_id());
        assertEquals(EntitiesHelper.SENDER, sms.getSender());
        assertEquals(EntitiesHelper.MSISDN, sms.getMsisdn());
        assertEquals(EntitiesHelper.TEXT, sms.getText());
        assertEquals(EntitiesHelper.SUBID, sms.getSubid());
        assertEquals(EntitiesHelper.ACKURL, sms.getAckurl());
        assertEquals(SMS.SMS_Status.SCHEDULED, sms.getSms_status());
        assertEquals(EntitiesHelper.DATETIME_SCHEDULED_2015, sms.getDatetimeScheduled());
        assertEquals(EntitiesHelper.DATETIME_SCHEDULED_2014, sms.getDatetimeLastModified());
        assertEquals(false, sms.isTest());
    }
}
