package com.opteral.springsms.sender;

import com.opteral.springsms.smsc.SMPPSessionBean;
import org.jsmpp.extra.SessionState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.annotation.PostConstruct;
import java.time.Instant;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


@Profile("!test")
@Configuration
public class SenderContext {
    @Autowired
    Sender sender;

    @Autowired
    SMPPSessionBean smppSessionBean;

    private static final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

    @PostConstruct
    public void init(){
        smppSessionBean.setUp();
        setupWorkers();
    }

    private void setupWorkers()
    {

        final Runnable runTasks = new Runnable()
        {
            public void run() {

                checkConnection();
                sendSMSScheduled();

            }
        };

        scheduledExecutorService.scheduleWithFixedDelay(runTasks,0, 5, TimeUnit.SECONDS);
    }

    private void checkConnection()
    {
        smppSessionBean.checkConnection();
    }

    private void sendSMSScheduled()
    {
        sender.send(new java.sql.Date(Instant.now().toEpochMilli()));
    }
}
