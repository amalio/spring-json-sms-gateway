package com.opteral.springsms.smsc;

import com.opteral.springsms.config.ConfigValues;
import org.jsmpp.bean.BindType;
import org.jsmpp.bean.NumberingPlanIndicator;
import org.jsmpp.bean.TypeOfNumber;
import org.jsmpp.session.BindParameter;
import org.jsmpp.session.MessageReceiverListener;
import org.jsmpp.session.SMPPSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Profile("!test")
@Component
public class SMPPSessionBean {

    @Autowired
    MessageReceiverListener smscListener;

    @Autowired
    SMSCSessionListener smscSessionListener;

    @Autowired
    SMPPSession smppSession;

    public SMPPSession getSmppSession() {
        return smppSession;
    }

    public void setUp() throws IOException {
        smppSession.setMessageReceiverListener(smscListener);

        smppSession.setTransactionTimer(5000L);

        smppSession.addSessionStateListener(smscSessionListener);

        connect();
    }

    public void connect() throws IOException {

        smppSession.connectAndBind(ConfigValues.SMSC_IP, ConfigValues.SMSC_PORT, new BindParameter(BindType.BIND_TRX, ConfigValues.SMSC_USERNAME, ConfigValues.SMSC_PASSWORD, "cp", TypeOfNumber.UNKNOWN, NumberingPlanIndicator.UNKNOWN, null));
    }

    public void disconnect()
    {
        smppSession.unbindAndClose();
    }

}
