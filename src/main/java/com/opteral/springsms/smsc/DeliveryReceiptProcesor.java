package com.opteral.springsms.smsc;

import com.opteral.springsms.model.ACK;
import com.opteral.springsms.model.SMS;
import org.jsmpp.bean.DeliveryReceipt;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("!test")
@Component
public class DeliveryReceiptProcesor {

    private DeliveryReceipt deliveryReceipt;

    public DeliveryReceipt getDeliveryReceipt() {
        return deliveryReceipt;
    }

    public void setDeliveryReceipt(DeliveryReceipt deliveryReceipt) {
        this.deliveryReceipt = deliveryReceipt;
    }

    public DeliveryReceiptProcesor() {
    }

    public ACK getACK() {
        ACK ack = new ACK();
        ack.setIdSMSC(getMessageId());
        ack.setSms_status(SMS.SMS_Status.DELIVRD);
        ack.setAckNow();
        ack.setDeliveredInfo(deliveryReceipt.toString());
        return ack;
    }

    private String getMessageId()
    {
        long id = Long.parseLong(deliveryReceipt.getId());
        return Long.toString(id, 16);
    }
}
