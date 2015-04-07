package com.opteral.springsms.sender;

import com.opteral.springsms.sender.Sender;
import com.opteral.springsms.smsc.SMPPSessionBean;
import org.jsmpp.extra.SessionState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.annotation.PostConstruct;
import java.time.Instant;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

@Profile("!test")
@Configuration
public class SenderContext {
    @Autowired
    Sender sender;

    @Autowired
    SMPPSessionBean smppSessionBean;

    public static final AtomicBoolean iniciado = new AtomicBoolean(false);
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

                tryToReconnect();
                sendSMSScheduled();

            }
        };


        scheduledExecutorService.scheduleWithFixedDelay(runTasks,0, 5, TimeUnit.SECONDS);

    }

    private void tryToReconnect()
    {
        //TODO Log this
        if (smppSessionBean.getSessionState() != SessionState.BOUND_TRX)
        {
            smppSessionBean.reconnect();
        }
    }


    private void sendSMSScheduled()
    {
        //TODO Log this
        sender.send(new java.sql.Date(Instant.now().toEpochMilli()));
    }
}
