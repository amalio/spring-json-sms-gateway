package com.opteral.springsms.functional;

import com.opteral.springsms.http.HttpHelper;
import com.opteral.springsms.json.JSON_SMS;
import com.opteral.springsms.json.RequestJSON;
import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

public class FunctionalTest {
    private static final Logger logger = Logger.getLogger(FunctionalTest.class);

    @Ignore
    @Test
    public void testFuncional() throws IOException {
        logger.info("Starting TEST |||||||||||||||||||| ------->>>>>>>>>>>>>>>> ");

        RequestJSON requestJSON = new RequestJSON();


        requestJSON.setSms_request(new ArrayList<JSON_SMS>());

        JSON_SMS  jsonsms = new JSON_SMS();

        jsonsms.setId(0);
        jsonsms.setMsisdn("34646548725");
        jsonsms.setSender("sender");
        jsonsms.setText("The SMS text with an ñ");
        jsonsms.setAck_url("http://www.anurl.com/ack");
        jsonsms.setSubid("subid1");
        jsonsms.setDatetime(null);
        jsonsms.setTest(false);


        for (int i=0; i<10; i++)
        {
            requestJSON.getSms_request().add(jsonsms);
        }

        String urlString = ("http://api.opteral.com/gateway");

        HttpHelper httpHelper = new HttpHelper(urlString,"amalio","secreto",requestJSON);


        try {
            logger.info(httpHelper.postRequest());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }

    }


}
