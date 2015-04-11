package com.opteral.springsms.config;

import com.opteral.springsms.sender.Sender;
import com.opteral.springsms.smsc.SMPPSessionBean;
import org.jsmpp.extra.SessionState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.time.Instant;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


@Profile("!test")
@Configuration
public class SenderConfig {
    @Autowired
    Sender sender;

    @Autowired
    SMPPSessionBean smppSessionBean;

    private static final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

    @PostConstruct
    public void init(){
        setUp();
        setupWorkers();
    }

    @PreDestroy
    public void disconnect()  {
       smppSessionBean.disconnect();
    }

    private void setupWorkers()
    {

        final Runnable runTasks = new Runnable()
        {
            public void run() {
                 sendOrConnect();
            }
        };

        scheduledExecutorService.scheduleWithFixedDelay(runTasks,0, 5, TimeUnit.SECONDS);
    }

    private void sendOrConnect()
    {
        if (isConnected())
            sendSMSScheduled();
        else
            connect();
    }

    private void setUp(){
        try {
            smppSessionBean.setUp();
        } catch (IOException ignored) {

        }
    }

    private void connect(){
        try {
            smppSessionBean.connect();
        } catch (IOException ignored) {

        }
    }

    private void sendSMSScheduled()
    {
        sender.send(new java.sql.Date(Instant.now().toEpochMilli()));
    }

    private boolean isConnected(){
        return smppSessionBean.getSmppSession().getSessionState().isBound();
    }
}
