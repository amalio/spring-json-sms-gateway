package com.opteral.springsms;

import com.opteral.springsms.database.SmsDao;
import com.opteral.springsms.exceptions.GatewayException;
import com.opteral.springsms.model.SMS;
import com.opteral.springsms.smsc.SMSC;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static com.opteral.springsms.database.EntitiesHelper.newSMS;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class SenderTest {

    private SmsDao smsDAO;
    private SMSC smsc;
    private Sender sender;
    private List<SMS> smsList = new ArrayList<SMS>();

    @Before
    public void init() throws GatewayException {

        smsDAO = mock(SmsDao.class);
        smsc = mock(SMSC.class);

        sender = new Sender(smsDAO, smsc);

        SMS sms1 = newSMS();
        SMS sms2 = newSMS();
        sms1.setId(1);
        sms2.setId(2);
        smsList.add(sms1);
        smsList.add(sms2);
    }

    @Test
    public void sendOkTest() throws SQLException, IOException, GatewayException {

        when(smsDAO.getSMSForSend(any(Date.class))).thenReturn(smsList);
        smsList.get(0).setIdSMSC("1");
        smsList.get(1).setIdSMSC("2");

        sender.send(new Date(Instant.now().toEpochMilli()));

        verify(smsc, times(1)).sendSMS(smsList.get(0));
        verify(smsc, times(1)).sendSMS(smsList.get(1));
        verify(smsDAO, times(1)).update(smsList.get(0));
        verify(smsDAO, times(1)).update(smsList.get(1));

    }

    @Test
    public void sendWithOneFailTest() throws SQLException, IOException, GatewayException {

        when(smsDAO.getSMSForSend(any(Date.class))).thenReturn(smsList);
        smsList.get(0).setIdSMSC("1");

        sender.send(new Date(Instant.now().toEpochMilli()));

        verify(smsc, times(1)).sendSMS(smsList.get(0));
        verify(smsDAO, times(1)).update(smsList.get(0));


    }
}
