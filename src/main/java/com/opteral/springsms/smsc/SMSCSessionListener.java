package com.opteral.springsms.smsc;


import com.opteral.springsms.config.RootConfig;
import org.apache.log4j.Logger;
import org.jsmpp.extra.SessionState;
import org.jsmpp.session.SessionStateListener;

public class SMSCSessionListener implements SessionStateListener {

    private static final Logger logger = Logger.getLogger(SMSCSessionListener.class);

    public void onStateChange(SessionState newState, SessionState oldState, Object source)
    {
        if (newState.equals(SessionState.CLOSED))
        {
            RootConfig.iniciado.set(false);

            logger.info("Session closed");

        }
    }
}
