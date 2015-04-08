package com.opteral.springsms.json;

import com.opteral.springsms.model.SMS;

import java.sql.Timestamp;

public class JSON_ACK {

    private long id;
    private String subid;
    private String msisdn;
    private Timestamp delivered;
    private SMS.SMS_Status sms_status;

    public JSON_ACK(long id, String subid, String msisdn, Timestamp delivered, SMS.SMS_Status sms_status) {
        this.id = id;
        this.subid = subid;
        this.msisdn = msisdn;
        this.delivered = delivered;
        this.sms_status = sms_status;
    }

    public JSON_ACK(SMS sms) {
        this.id = sms.getId();
        this.subid = sms.getSubid();
        this.msisdn = sms.getMsisdn();
        this.delivered = sms.getDatetimeLastModified();
        this.sms_status = sms.getSms_status();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSubid() {
        return subid;
    }

    public void setSubid(String subid) {
        this.subid = subid;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public Timestamp getDelivered() {
        return delivered;
    }

    public void setDelivered(Timestamp delivered) {
        this.delivered = delivered;
    }

    public SMS.SMS_Status getSms_status() {
        return sms_status;
    }

    public void setSms_status(SMS.SMS_Status sms_status) {
        this.sms_status = sms_status;
    }
}
