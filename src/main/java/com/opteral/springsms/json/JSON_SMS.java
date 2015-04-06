package com.opteral.springsms.json;

import java.sql.Timestamp;

public class JSON_SMS {

    private int id;
    private String msisdn;
    private String sender;
    private String text;
    private String subid;
    private String ack_url;
    private Timestamp datetime;
    private boolean test;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSubid() {
        return subid;
    }

    public void setSubid(String subid) {
        this.subid = subid;
    }

    public String getAck_url() {
        return ack_url;
    }

    public void setAck_url(String ack_url) {
        this.ack_url = ack_url;
    }

    public Timestamp getDatetime() {
        return datetime;
    }

    public void setDatetime(Timestamp datetime) {
        this.datetime = datetime;
    }

    public boolean isTest() {
        return test;
    }

    public void setTest(boolean test) {
        this.test = test;
    }
}
