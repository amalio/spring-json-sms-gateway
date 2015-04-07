package com.opteral.springsms.smsc;

import com.opteral.springsms.config.ConfigValues;
import org.jsmpp.bean.BindType;
import org.jsmpp.bean.NumberingPlanIndicator;
import org.jsmpp.bean.TypeOfNumber;
import org.jsmpp.extra.SessionState;
import org.jsmpp.session.BindParameter;
import org.jsmpp.session.MessageReceiverListener;
import org.jsmpp.session.SMPPSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SMPPSessionBean extends SMPPSession {

    @Autowired
    MessageReceiverListener smscListener;

    public void setUp()
    {
        setMessageReceiverListener(smscListener);

        setTransactionTimer(5000L);

        addSessionStateListener(new SMSCSessionListener());

        connect();
    }

    public void reconnect()
    {
        if (getSessionState() != SessionState.BOUND_TRX)
        {
            connect();
        }
    }

    private void connect()
    {
        try {
            connectAndBind(ConfigValues.SMSC_IP, ConfigValues.SMSC_PORT, new BindParameter(BindType.BIND_TRX, ConfigValues.SMSC_USERNAME, ConfigValues.SMSC_PASSWORD, "cp", TypeOfNumber.UNKNOWN, NumberingPlanIndicator.UNKNOWN, null));
        }
        catch (Exception ignored) {
        }
    }

    @Override
    public void close() {
        unbindAndClose();
    }
}
