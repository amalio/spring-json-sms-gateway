package com.opteral.springsms.config;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;


@Aspect
public class LoggerAOP {
    private static final Logger logger = Logger.getLogger(LoggerAOP.class);


    @After("execution(* com.opteral.springsms.smsc.SMSCListener.onAcceptDeliverSm(..))")
    public void logAfter(JoinPoint joinPoint) {

        logger.info("SMSCListerner -> incoming deliverSM");

    }


    @Before("execution(* com.opteral.springsms.Sender.send(..))")
    public void beforeSend2() {

        logger.info("Sender -> Sending pending sms...");

    }
}
