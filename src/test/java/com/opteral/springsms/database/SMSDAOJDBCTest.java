package com.opteral.springsms.database;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import com.github.springtestdbunit.dataset.ReplacementDataSetLoader;
import com.opteral.springsms.config.RootConfig;
import com.opteral.springsms.model.ACK;
import com.opteral.springsms.model.SMS;
import com.opteral.springsms.web.WebConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.sql.DataSource;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import static com.opteral.springsms.database.EntitiesHelper.newSMS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebConfig.class, RootConfig.class})
@ActiveProfiles("test")
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class, DirtiesContextTestExecutionListener.class })
@DbUnitConfiguration(dataSetLoader = ReplacementDataSetLoader.class)
@DatabaseTearDown("/dataset/user.xml")
public class SmsDaoJDBCTest {

    @Autowired
    @Qualifier("dataSource")
    private DataSource dataSource;

    @Autowired
    private SmsDaoJDBC smsDaoJDBC;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    @Test
    @DatabaseSetup("/dataset/sms-instant.xml")
    public void getSMSTest() throws Exception {
        SMS sms = smsDaoJDBC.getSMS(EntitiesHelper.SMS_ID);

        assertNotNull(sms);
        assertEquals(EntitiesHelper.SMS_ID, sms.getId());
        assertEquals(EntitiesHelper.USER_ID, sms.getUser_id());
        assertEquals(EntitiesHelper.SENDER, sms.getSender());
        assertEquals(EntitiesHelper.MSISDN, sms.getMsisdn());
        assertEquals(EntitiesHelper.TEXT, sms.getText());
        assertEquals(EntitiesHelper.SUBID, sms.getSubid());
        assertEquals(EntitiesHelper.ACKURL, sms.getAckurl());
        assertEquals(null, sms.getDatetimeScheduled());
        assertEquals(SMS.SMS_Status.ACCEPTD, sms.getSms_status());
        assertEquals(false, sms.isTest());

    }

    @Test
    @DatabaseSetup("/dataset/sms-onsmsc.xml")
    public void getSMSTestFromIdSMSC() throws Exception {
        SMS sms = smsDaoJDBC.getSMSfromIdSMSC("idSMSC1");
        assertNotNull(sms);
        assertEquals(1, sms.getId());
        assertEquals(EntitiesHelper.USER_ID, sms.getUser_id());
        assertEquals(EntitiesHelper.SENDER, sms.getSender());
        assertEquals(EntitiesHelper.MSISDN, sms.getMsisdn());
        assertEquals(EntitiesHelper.TEXT, sms.getText());
        assertEquals(EntitiesHelper.SUBID, sms.getSubid());
        assertEquals(EntitiesHelper.ACKURL, sms.getAckurl());
        assertEquals(SMS.SMS_Status.ONSMSC, sms.getSms_status());
    }

    @Test
    @DatabaseSetup("/dataset/empty.xml")
    @ExpectedDatabase(value="/dataset/noid-sms-scheduled.xml", assertionMode= DatabaseAssertionMode.NON_STRICT_UNORDERED)
    public void insertSMSTest() throws Exception {

        SMS sms = newSMS();
        sms.setSms_status(SMS.SMS_Status.SCHEDULED);
        smsDaoJDBC.insert(sms);


    }

    @Test
    @DatabaseSetup("/dataset/sms-scheduled.xml")
    @ExpectedDatabase(value= "/dataset/sms-scheduled-updated.xml", assertionMode= DatabaseAssertionMode.NON_STRICT_UNORDERED)
    public void updateSMSTest() throws Exception {

        SMS sms = newSMS();
        sms.setSms_status(SMS.SMS_Status.SCHEDULED);
        sms.setDatetimeScheduled(EntitiesHelper.DATETIME_SCHEDULED_2015);
        sms.setSubid("subid updated");
        smsDaoJDBC.update(sms);


    }

    @Test
    @DatabaseSetup("/dataset/sms-onsmsc.xml")
    @ExpectedDatabase(value= "/dataset/sms-delivered.xml", assertionMode= DatabaseAssertionMode.NON_STRICT_UNORDERED)
    public void updateStatusTest() throws Exception {

        ACK ack = new ACK();
        ack.setIdSMSC("idSMSC1");
        ack.setSms_status(SMS.SMS_Status.DELIVRD);
        ack.setAckNow();
        smsDaoJDBC.updateSMS_Status(ack);

    }

    @Test
    @DatabaseSetup("/dataset/sms-forsend.xml")
    public void getSMSForSendTest() throws Exception {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date date = formatter.parse("2015-01-01 10:30:00");

        List<SMS> lista = smsDaoJDBC.getSMSForSend(new Date(date.getTime()));

        assertEquals(2, lista.size());

        assertEquals(1, lista.get(0).getId());
        assertEquals(2, lista.get(1).getId());
        assertEquals(EntitiesHelper.USER_ID, lista.get(0).getUser_id());
        assertEquals(EntitiesHelper.USER_ID, lista.get(1).getUser_id());
        assertEquals(EntitiesHelper.SENDER, lista.get(0).getSender());
        assertEquals(EntitiesHelper.SENDER, lista.get(1).getSender());
        assertEquals(EntitiesHelper.MSISDN, lista.get(0).getMsisdn());
        assertEquals(EntitiesHelper.MSISDN, lista.get(1).getMsisdn());
        assertEquals(EntitiesHelper.TEXT, lista.get(0).getText());
        assertEquals(EntitiesHelper.TEXT, lista.get(1).getText());
        assertEquals(EntitiesHelper.SUBID, lista.get(0).getSubid());
        assertEquals(EntitiesHelper.SUBID, lista.get(1).getSubid());
        assertEquals(EntitiesHelper.ACKURL, lista.get(0).getAckurl());
        assertEquals(EntitiesHelper.ACKURL, lista.get(1).getAckurl());
        assertEquals(null, lista.get(0).getDatetimeScheduled());
        assertEquals(EntitiesHelper.DATETIME_SCHEDULED_2014, lista.get(1).getDatetimeScheduled());

    }
}
