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
    @Qualifier("smsdaojdbc")
    private SmsDaoJDBC smsDaoJDBC;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    @Test
    @DatabaseSetup("/dataset/sms-instant.xml")
    public void testgetSMSSMS() throws Exception {
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
    @DatabaseSetup("/dataset/empty.xml")
    @ExpectedDatabase(value="/dataset/noid-sms-scheduled.xml", assertionMode= DatabaseAssertionMode.NON_STRICT_UNORDERED)
    public void testInsertSMS() throws Exception {

        SMS sms = newSMS();
        sms.setSms_status(SMS.SMS_Status.SCHEDULED);
        smsDaoJDBC.insert(sms);


    }

    @Test
    @DatabaseSetup("/dataset/sms-scheduled.xml")
    @ExpectedDatabase(value= "/dataset/sms-scheduled-updated.xml", assertionMode= DatabaseAssertionMode.NON_STRICT_UNORDERED)
    public void testUpdateSMS() throws Exception {

        SMS sms = newSMS();
        sms.setSms_status(SMS.SMS_Status.SCHEDULED);
        sms.setDatetimeScheduled(EntitiesHelper.DATETIME_SCHEDULED_2015);
        sms.setSubid("subid updated");
        smsDaoJDBC.update(sms);


    }

    @Test
    @DatabaseSetup("/dataset/sms-onsmsc.xml")
    @ExpectedDatabase(value= "/dataset/sms-delivered.xml", assertionMode= DatabaseAssertionMode.NON_STRICT_UNORDERED)
    public void testUpdateStatus() throws Exception {

        ACK ack = new ACK();
        ack.setIdSMSC("idSMSC1");
        ack.setSms_status(SMS.SMS_Status.DELIVRD);
        ack.setAckNow();
        smsDaoJDBC.updateSMS_Status(ack);

    }
}
