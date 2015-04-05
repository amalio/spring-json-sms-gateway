package com.opteral.springsms.json;

import java.util.ArrayList;
import java.util.List;

public class RequestJSON {


    private List<JSON_SMS> sms_request = new ArrayList<JSON_SMS>();

    public List<JSON_SMS> getSms_request() {
        return sms_request;
    }

    public void setSms_request(List<JSON_SMS> sms_request) {
        this.sms_request = sms_request;
    }

    public void addSMS(JSON_SMS json_sms)
    {
        this.sms_request.add(json_sms);
    }
}
