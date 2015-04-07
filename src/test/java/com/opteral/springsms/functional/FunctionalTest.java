package com.opteral.springsms.functional;

import com.opteral.springsms.TestHelper;
import com.opteral.springsms.json.JSON_SMS;
import com.opteral.springsms.json.Parser;
import com.opteral.springsms.json.RequestJSON;
import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;

import java.io.*;
import java.net.*;
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


        for (int i=0; i<5; i++)
        {
            requestJSON.getSms_request().add(jsonsms);
        }

        URL url = new URL("http://api.opteral.com/gateway");
        byte[] post = TestHelper.convertRequestJSONtoBytes(requestJSON);


        try {
            logger.info(postRequest(url, post));
        } catch (IOException e) {
            logger.error(e.getMessage());
        }

    }

    private String postRequest(URL url, byte[] post) throws IOException {

        InputStream inputStream = null;

        try
        {
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            Authenticator.setDefault(new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("amalio", "secreto".toCharArray());
                }
            });

            OutputStream os = conn.getOutputStream();
            os.write(post);

            conn.connect();


            inputStream = conn.getInputStream();


            return inputStreamToString(inputStream);


        }
        catch (Exception e) {
            return e.getMessage();
        }
        finally
        {
            if (inputStream != null) {
                inputStream.close();
            }
        }


    }

    private String inputStreamToString(InputStream is) throws IOException {

        String line = "";
        StringBuilder total = new StringBuilder();


        BufferedReader rd = new BufferedReader(new InputStreamReader(is));


        while ((line = rd.readLine()) != null) {
            total.append(line);
        }


        return total.toString();
    }
}
