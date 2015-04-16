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

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

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

    @Test (expected = GatewayException.class)
    @Transactional
    public void getSMSTestFromIdSMSCException() throws Exception {

        SMS sms = smsDaoHibernate.getSMSfromIdSMSC("badID");

    }

    @Test
    @Transactional
    public void deleteSMS() throws Exception {

        SMS sms = smsDaoHibernate.getSMS(1);
        smsDaoHibernate.delete(sms);
        sms = smsDaoHibernate.getSMS(1);
        assertNull(sms);

    }

    @Test
    @Transactional
    public void getSMSFrofSend() throws Exception {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date date = formatter.parse("2015-01-01 10:30:00");

        List<SMS> lista = smsDaoHibernate.getSMSForSend(new Date(date.getTime()));

        assertEquals(2, lista.size());
        assertEquals("subid2", lista.get(0).getSubid());
        assertEquals("subid3", lista.get(1).getSubid());

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
