package com.opteral.springsms.database;

import com.opteral.springsms.config.RootConfig;
import com.opteral.springsms.exceptions.GatewayException;
import com.opteral.springsms.model.SMS;
import com.opteral.springsms.model.User;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static com.opteral.springsms.database.EntitiesHelper.newSMS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

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

        assertSMS(0,sms);
    }


    @Test ()
    @Transactional
    public void getSMSTestNoSMS() throws GatewayException {

        SMS sms = smsDaoHibernate.getSMS(0);
        assertNull(sms);

    }

    @Test
    @Transactional
    public void getSMSTestFromIdSMSC() throws Exception {

        SMS sms = smsDaoHibernate.getSMSfromIdSMSC("idSMSC1");

        assertSMS(0,sms);
    }

    private static void assertSMS(int expectedSMSIndex, SMS actual) {
        SMS expected = ArraySMS[expectedSMSIndex];
        assertNotNull(actual);
        assertEquals(actual.getId(), actual.getId());
        assertEquals(actual.getUser_id(), expected.getUser_id());
        assertEquals(actual.getSender(), expected.getSender());
        assertEquals(actual.getMsisdn(), expected.getMsisdn());
        assertEquals(actual.getText(), expected.getText());
        assertEquals(actual.getSubid(), expected.getSubid());
        assertEquals(actual.getAckurl(), expected.getAckurl());
        assertEquals(actual.getSms_status(), expected.getSms_status());
        assertEquals(actual.getDatetimeLastModified(), expected.getDatetimeLastModified());
        assertEquals(actual.getDatetimeScheduled(), expected.getDatetimeScheduled());
    }


    private static SMS[] ArraySMS = new SMS[1];
    @BeforeClass
    public static void before() {
        SMS sms1= newSMS();
        ArraySMS[0] = sms1;

    }


}
