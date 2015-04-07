package com.opteral.springsms.smsc;


import com.opteral.springsms.sender.SenderContext;
import org.apache.log4j.Logger;
import org.jsmpp.extra.SessionState;
import org.jsmpp.session.SessionStateListener;

//TODO MAkeme a bean!
public class SMSCSessionListener implements SessionStateListener {

    private static final Logger logger = Logger.getLogger(SMSCSessionListener.class);

    public void onStateChange(SessionState newState, SessionState oldState, Object source)
    {
        if (newState.equals(SessionState.CLOSED))
        {
            SenderContext.iniciado.set(false);

            //TODO log this

        }
    }
}
