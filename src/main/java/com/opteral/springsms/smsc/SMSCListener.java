package com.opteral.springsms.smsc;


import com.opteral.springsms.sender.ACKSender;
import com.opteral.springsms.database.SmsDao;
import com.opteral.springsms.database.SmsDaoJDBC;
import com.opteral.springsms.exceptions.GatewayException;
import com.opteral.springsms.model.ACK;
import org.jsmpp.bean.*;
import org.jsmpp.extra.ProcessRequestException;
import org.jsmpp.session.DataSmResult;
import org.jsmpp.session.MessageReceiverListener;
import org.jsmpp.session.Session;
import org.jsmpp.util.InvalidDeliveryReceiptException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;


@Profile("!test")
@Component("smscListener")
public class SMSCListener implements MessageReceiverListener {

    @Autowired
    SmsDaoJDBC smsdao;

    @Autowired
    DeliveryReceiptProcesor deliveryReceiptProcesor;

    @Override
    public void onAcceptDeliverSm(DeliverSm deliverSm) throws ProcessRequestException {

        if (MessageType.SMSC_DEL_RECEIPT.containedIn(deliverSm.getEsmClass())) {

            try
            {
                DeliveryReceipt delReceipt = deliverSm.getShortMessageAsDeliveryReceipt();
                deliveryReceiptProcesor.setDeliveryReceipt(delReceipt);
                final ACK ack = deliveryReceiptProcesor.getACK();


                Runnable r = new Runnable()
                {
                    @Override
                    public void run()
                    {
                        try {
                            processACK(smsdao,ack);
                        } catch (GatewayException ignored) {

                        }
                    }
                };
                new Thread(r).start();

            }
            catch (InvalidDeliveryReceiptException ignored) {

            }
        }
        else {

            try{
               throw new Exception("Incoming SMSC message : " + new String(deliverSm.getShortMessage()));
            }
            catch (Exception ignore) {}

        }

    }

    @Override
    public void onAcceptAlertNotification(AlertNotification alertNotification) {

    }

    @Override
    public DataSmResult onAcceptDataSm(DataSm dataSm, Session session) throws ProcessRequestException {
        return null;
    }

    private void processACK(SmsDao smsDao, ACK ack) throws GatewayException {
        smsDao.updateSMS_Status(ack);

        ACKSender.sendACK(ack, smsDao);
    }






}
