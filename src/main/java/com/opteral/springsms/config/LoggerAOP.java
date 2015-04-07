package com.opteral.springsms.config;

import com.opteral.springsms.model.ACK;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.jsmpp.bean.AlertNotification;


@Aspect
public class LoggerAOP {
    private static final Logger logger = Logger.getLogger(LoggerAOP.class);


    @AfterThrowing(pointcut ="execution(* com.opteral.springsms.smsc.SMSCListener.onAcceptDeliverSm(..))", throwing="error")
    public void logAfter(Throwable error) {

        logger.error("SMSCListerner: " + error.getMessage());

    }

    @AfterReturning(pointcut ="execution(* com.opteral.springsms.smsc.SMSCListener.onAcceptAlertNotification(..))")
    public void logAfter(JoinPoint joinPoint) {
        AlertNotification alertNotification = (AlertNotification)joinPoint.getArgs()[0];
        logger.warn("Incoming SMSC alert : " + alertNotification.getCommandId() + alertNotification.toString());

    }


    @Before("execution(* com.opteral.springsms.sender.Sender.send(..))")
    public void beforeSend2() {

        logger.info("Sender -> Sending pending sms...");

    }

    @AfterReturning(pointcut = "execution(* com.opteral.springsms.smsc.DeliveryReceiptProcesor.getACK(..))", returning="result")
    public void afterProcessACK(Object result) {
        ACK ack = (ACK)result;
        logger.info("DeliveryReceiptProcesor -> "+ack.getIdSMSC()+" "+ack.getDeliveredInfo());

    }

    @AfterThrowing(pointcut ="execution(* com.opteral.springsms.smsc.SMPPSessionBean.*(..))", throwing="error")
    public void failsOnSMPPSessionBean(Throwable error) {
        logger.error("SMSCListerner: " + error.getMessage());
    }
}
