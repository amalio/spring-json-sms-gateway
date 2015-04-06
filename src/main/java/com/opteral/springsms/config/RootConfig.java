package com.opteral.springsms.config;

import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;

import com.opteral.springsms.Utilities;
import com.opteral.springsms.smsc.SMSCListener;
import com.opteral.springsms.smsc.SMSCSessionListener;
import com.opteral.springsms.web.WebConfig;


import org.apache.log4j.Logger;
import org.jsmpp.bean.BindType;
import org.jsmpp.bean.NumberingPlanIndicator;
import org.jsmpp.bean.TypeOfNumber;
import org.jsmpp.extra.SessionState;
import org.jsmpp.session.BindParameter;
import org.jsmpp.session.SMPPSession;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.*;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.core.type.filter.RegexPatternTypeFilter;

import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.annotation.PostConstruct;

@Configuration
@ComponentScan(basePackages={"com.opteral.springsms"},
    excludeFilters={
        @Filter(type=FilterType.ANNOTATION, value=EnableWebMvc.class)
    })
public class RootConfig {

    @Autowired
    @Qualifier("config_swich")
    boolean configSwich;

    final Logger logger = Logger.getLogger(RootConfig.class);
    public static final AtomicBoolean iniciado = new AtomicBoolean(false);
    private static SMPPSession session = null;
    private static final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

    @PostConstruct
    public void init()
    {
        if (isTest())
        {
            logger.info("|||||||||||||| TEST CONTEXT ||||||||||||||");
            return;
        }


        loadConfig();
        setupSMPP();
        setupWorkers();

        logger.info("|||||||||||||| GATEWAY STARTED ||||||||||||||");
    }

    private void loadConfig()
    {
        try {
            Utilities.getConfig(isTest());
        }
        catch (Exception e)
        {
            throw new RuntimeException("Failed loading configuration file");
        }
    }

    private void setupSMPP()
    {

        try
        {
            session = new SMPPSession();

            session.setMessageReceiverListener(new SMSCListener());

            session.setTransactionTimer(5000L);

            session.addSessionStateListener(new SMSCSessionListener());

            session.connectAndBind(ConfigValues.SMSC_IP, ConfigValues.SMSC_PORT, new BindParameter(BindType.BIND_TRX, ConfigValues.SMSC_USERNAME, ConfigValues.SMSC_PASSWORD, "cp", TypeOfNumber.UNKNOWN, NumberingPlanIndicator.UNKNOWN, null));

            iniciado.compareAndSet(false, true);

        }
        catch (IOException e)
        {
            logger.error("Failed connect and bind to host", e);
        }
        catch (Exception e)
        {
            logger.error("Failed connect and bind to host", e);
        }

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
        if (session.getSessionState() != SessionState.BOUND_TRX)
        {
            try {
                logger.info("Running scheduled task - trying to reconnect");

                setupSMPP();
            } catch (Exception e) {
                logger.error("Could not reconnect");
            }
        }
    }


    private void sendSMSScheduled()
    {
        logger.info("Running scheduled task - sending SMS");
        //send
    }

    public static SMPPSession getSession() {
        return session;
    }



    private void disconnectSMPP()
    {
        session.unbindAndClose();
    }

    private boolean isTest()
    {
        return configSwich;
    }
}
