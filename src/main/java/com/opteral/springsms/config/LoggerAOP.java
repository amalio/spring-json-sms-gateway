package com.opteral.springsms.config;

import com.opteral.springsms.model.ACK;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.jsmpp.bean.AlertNotification;
import org.jsmpp.extra.SessionState;


@Aspect
public class LoggerAOP {
    private static final Logger logger = Logger.getLogger(LoggerAOP.class);


    @AfterThrowing(pointcut ="execution(* com.opteral.springsms.smsc.SMSCListener.onAcceptDeliverSm(..))", throwing="error")
    public void logAfter(Throwable error) {

        logger.error("SMSCListerner -> " + error.getMessage());

    }

    @AfterReturning(pointcut ="execution(* com.opteral.springsms.smsc.SMSCListener.onAcceptAlertNotification(..))")
    public void logAfter(JoinPoint joinPoint) {
        AlertNotification alertNotification = (AlertNotification)joinPoint.getArgs()[0];
        logger.warn("SMSCListener -> Incoming SMSC alert : " + alertNotification.getCommandId() + alertNotification.toString());

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
        logger.error("SMPPSessionBean -> " + error.getMessage());
    }

    @AfterReturning(pointcut ="execution(* com.opteral.springsms.smsc.SMPPSessionBean.setUp(..))")
    public void afterSetup() {
        logger.info("SMPPSessionBean -> setup complete");
    }

    @AfterReturning(pointcut ="execution(* com.opteral.springsms.smsc.SMPPSessionBean.disconnect(..))")
    public void disconnected() {
        logger.info("SMPPSessionBean -> disconnected");
    }

    @AfterReturning(pointcut = "execution(* com.opteral.springsms.smsc.SMSCSessionListener.onStateChange(..))")
    public void afterSessionListener(JoinPoint joinPoint) {
        SessionState newState = (SessionState)joinPoint.getArgs()[0];
        logger.info("SessionListener -> new session state :" + newState.toString());
    }

    @AfterReturning(pointcut ="execution(* com.opteral.springsms.smsc.SMSCImp.sendSMS(..))", returning="result")
    public void onSubmitShortMessage2(Object result) {
        if (!result.equals(""))
            logger.info("SMSCImp -> Message submitted, message_id is " + result);

    }

    @AfterThrowing(pointcut ="execution(* com.opteral.springsms.sender.ACKSender.sendACK(..))", throwing="error")
    public void failsOnSendACK(Throwable error) {
        logger.error("ACKSender -> Failed sending ACK: "+error.getMessage());
    }

    @AfterReturning(pointcut ="execution(* com.opteral.springsms.sender.ACKSender.sendACK(..))", returning="result")
    public void afterSendingACK(Object result) {
        if (!result.equals(""))
            logger.info("HttpGetSender -> ACK GET sended: "+result);
    }

    @AfterThrowing(pointcut ="execution(* com.opteral.springsms.database.SmsDao.*(..))", throwing="error")
    public void failsOnSMSDao(Throwable error) {
        logger.error("SmsDaoHibernate -> Failed: "+error.getMessage());
    }

    @AfterThrowing(pointcut ="execution(* com.opteral.springsms.database.UserDao.*(..))", throwing="error")
    public void failsOnSMSUser(Throwable error) {
        logger.error("UserDaoHibernate -> Failed: "+error.getMessage());
    }

}
