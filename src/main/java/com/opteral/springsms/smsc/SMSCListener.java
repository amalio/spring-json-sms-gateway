package com.opteral.springsms.smsc;


import com.opteral.springsms.ACKSender;
import com.opteral.springsms.database.SMSDAO;
import com.opteral.springsms.database.SMSDAOJDBC;
import com.opteral.springsms.exceptions.GatewayException;
import com.opteral.springsms.model.ACK;
import com.opteral.springsms.model.SMS;
import org.apache.log4j.Logger;
import org.jsmpp.bean.*;
import org.jsmpp.extra.ProcessRequestException;
import org.jsmpp.session.DataSmResult;
import org.jsmpp.session.MessageReceiverListener;
import org.jsmpp.session.Session;
import org.jsmpp.util.InvalidDeliveryReceiptException;

import java.sql.SQLException;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public class SMSCListener implements MessageReceiverListener {

    private static final Logger logger = Logger.getLogger(SMSCListener.class);

    @Override
    public void onAcceptDeliverSm(DeliverSm deliverSm) throws ProcessRequestException {

        if (MessageType.SMSC_DEL_RECEIPT.containedIn(deliverSm.getEsmClass())) {


            try
            {

                DeliveryReceipt delReceipt = deliverSm.getShortMessageAsDeliveryReceipt();
                long id = Long.parseLong(delReceipt.getId());
                String messageId = Long.toString(id, 16);

                final ACK ack = new ACK();

                ack.setIdSMSC(messageId);
                ack.setSms_status(SMS.SMS_Status.DELIVRD);
                ack.setAckNow();


                final SMSDAOJDBC smsdaojdbc = new SMSDAOJDBC();

                processACK(smsdaojdbc, ack);

                Runnable r = new Runnable()
                {
                    @Override
                    public void run()
                    {
                        try {
                            processACK(smsdaojdbc,ack);
                        } catch (GatewayException e) {
                            logger.error("Failed sending ACK", e);
                        }
                    }
                };
                new Thread(r).start();

                logger.info("Recived confirmation delivery: '" + messageId + "' : " + delReceipt +delReceipt.getDelivered());

            }
            catch (InvalidDeliveryReceiptException e)
            {
                logger.error("Failed getting delivery receipt", e);
            }

            catch (GatewayException e)
            {
                logger.error("Failed updating sms_status on databse", e);
            }

        }
        else
        {
            logger.info("Incoming SMSC message : " + new String(deliverSm.getShortMessage()));
        }

    }

    @Override
    public void onAcceptAlertNotification(AlertNotification alertNotification) {

        logger.warn("Incoming SMSC alert : " + alertNotification.getCommandId() + alertNotification.toString());
    }

    @Override
    public DataSmResult onAcceptDataSm(DataSm dataSm, Session session) throws ProcessRequestException {
        return null;
    }

    private void processACK(SMSDAO smsdao, ACK ack) throws GatewayException {

        smsdao.updateSMS_Status(ack);

        ACKSender.sendACK(ack, smsdao);
    }






}
