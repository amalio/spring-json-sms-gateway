package com.opteral.springsms.smsc;


import org.jsmpp.extra.SessionState;
import org.jsmpp.session.SessionStateListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("!test")
@Component
public class SMSCSessionListener implements SessionStateListener {

    @Override
    public void onStateChange(SessionState newState, SessionState oldState, Object source)
    {
        //called for AOP logging
    }
}
