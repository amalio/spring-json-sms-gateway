package com.opteral.springsms.validation;

import com.opteral.springsms.TestHelper;
import com.opteral.springsms.config.ConfigValues;
import com.opteral.springsms.exceptions.ValidationException;
import com.opteral.springsms.json.JSON_SMS;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import static org.junit.Assert.fail;

@RunWith(Parameterized.class)
public class CheckerSMSTest {




    @Parameterized.Parameters
    public static Collection<Object[]> data() throws ParseException {

        JSON_SMS okInstantSend = new JSON_SMS();
        JSON_SMS okInstantSend2 = new JSON_SMS();
        JSON_SMS okDated = new JSON_SMS();

        JSON_SMS withoutSender = new JSON_SMS();
        JSON_SMS withoutText = new JSON_SMS();
        JSON_SMS badText1 = new JSON_SMS();
        JSON_SMS badText2 = new JSON_SMS();
        JSON_SMS badMsisdn = new JSON_SMS();
        JSON_SMS badSender = new JSON_SMS();
        JSON_SMS badSubid = new JSON_SMS();
        JSON_SMS badAckUrl = new JSON_SMS();
        JSON_SMS ackUrlWithoutSubId = new JSON_SMS();
        JSON_SMS withoutDatetime = new JSON_SMS();


        okInstantSend.setMsisdn("34646974525");
        okInstantSend.setSender("sender");
        okInstantSend.setText("the text of SMS with an ñ");
        okInstantSend.setSubid("subid1");
        okInstantSend.setAck_url("http://wwww.web.com/ack.php");
        okInstantSend.setTest(false);

        okInstantSend2.setMsisdn("34646974525");
        okInstantSend2.setSender("sender");
        okInstantSend2.setText(TestHelper.genString(ConfigValues.MAX_SMS_SIZE));

        okDated.setId(500);
        okDated.setMsisdn("34646984536");
        okDated.setSender("sender");
        okDated.setText("the text of SMS with an ñ");
        okDated.setSubid("subid1");
        okDated.setAck_url("http://wwww.web.com/ack.php");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = formatter.parse("2015-12-27 10:00");
        okDated.setDatetime(new Timestamp(date.getTime()));
        okDated.setTest(false);

        withoutText.setMsisdn("34646984536");
        withoutText.setSender("sender");
        withoutText.setSubid("subid1");
        withoutText.setAck_url("http://wwww.web.com/ack.php");
        withoutText.setTest(false);

        badText1.setMsisdn("34646984536");
        badText1.setSender("sender");
        badText1.setText("");
        badText1.setSubid("subid1");
        badText1.setAck_url("http://wwww.web.com/ack.php");
        badText1.setTest(false);
        

        badText2.setMsisdn("34646984536");
        badText2.setSender("sender");
        badText2.setText(TestHelper.genString(ConfigValues.MAX_SMS_SIZE + 1));
        badText2.setSubid("subid1");
        badText2.setAck_url("http://wwww.web.com/ack.php");
        badText2.setTest(false);
        

        badMsisdn.setMsisdn("+34646984536");
        badMsisdn.setSender("sender");
        badMsisdn.setText("the text of SMS with an ñ");
        badMsisdn.setSubid("subid1");
        badMsisdn.setAck_url("http://wwww.web.com/ack.php");
        badMsisdn.setTest(false);

        badSender.setMsisdn("34646984536");
        badSender.setSender("Clinica Dental");
        badSender.setText("the text of SMS with an ñ");
        badSender.setSubid("subid1");
        badSender.setAck_url("http://wwww.web.com/ack.php");
        badSender.setTest(false);

        withoutSender.setMsisdn("34646984536");
        withoutSender.setText("the text of SMS with an ñ");
        withoutSender.setSubid("subid1");
        withoutSender.setAck_url("http://wwww.web.com/ack.php");
        withoutSender.setTest(false);

        badSubid.setMsisdn("34646984536");
        badSubid.setSender("sender");
        badSubid.setText("the text of SMS with an ñ");
        badSubid.setSubid("subid1 3ñlkdsañdlskdñsalkd3lfsdlñfkñsdlfkñsdlfksdñlfskdñfskdñflskdkjhkjhk");
        badSubid.setAck_url("http://wwww.web.com/ack.php");
        badSubid.setTest(false);


        badAckUrl.setMsisdn("34646984536");
        badAckUrl.setSender("sender");
        badAckUrl.setText("the text of SMS with an ñ");
        badAckUrl.setSubid("subid1");
        badAckUrl.setAck_url("www.web.com/ack.php");
        badAckUrl.setTest(false);

        ackUrlWithoutSubId.setMsisdn("34646984536");
        ackUrlWithoutSubId.setSender("sender");
        ackUrlWithoutSubId.setText("the text of SMS with an ñ");
        ackUrlWithoutSubId.setAck_url("http://wwww.web.com/ack.php");
        ackUrlWithoutSubId.setTest(false);

        withoutDatetime.setId(500);
        withoutDatetime.setMsisdn("34646984536");
        withoutDatetime.setSender("sender");
        withoutDatetime.setText("the text of SMS with an ñ");
        withoutDatetime.setSubid("subid1");
        withoutDatetime.setAck_url("http://wwww.web.com/ack.php");
        Date date2 = formatter.parse("2014-12-27 10:00");
        withoutDatetime.setDatetime(new Timestamp(date2.getTime()));



        return Arrays.asList(new Object[][]{

                {TestHelper.NORMAL, okInstantSend},
                {TestHelper.NORMAL, okInstantSend2},
                {TestHelper.NORMAL, okDated},



                {TestHelper.EXCEPTION_EXPECTED, withoutSender},
                {TestHelper.EXCEPTION_EXPECTED, badText1},
                {TestHelper.EXCEPTION_EXPECTED, withoutText},
                {TestHelper.EXCEPTION_EXPECTED, badText2},
                {TestHelper.EXCEPTION_EXPECTED, badMsisdn},
                {TestHelper.EXCEPTION_EXPECTED, badSender},
                {TestHelper.EXCEPTION_EXPECTED, badSubid},
                {TestHelper.EXCEPTION_EXPECTED, badAckUrl},
                {TestHelper.EXCEPTION_EXPECTED, ackUrlWithoutSubId},
                {TestHelper.EXCEPTION_EXPECTED, withoutDatetime}


        });


    }

    @Parameterized.Parameter
    public int expected;

    @Parameterized.Parameter (value = 1)
    public JSON_SMS jsonsms;


    @Test
    public void test() throws ValidationException {

        CheckerSMS checkerSMS = new CheckerSMS(new ValidatorImp());


        if (expected == TestHelper.EXCEPTION_EXPECTED)
        {

            try
            {
                checkerSMS.check(jsonsms);
                fail("didn't throw an exception!");
            } catch (ValidationException e) {
                // Test succeded!
            }
        }
        else
        {
            checkerSMS.check(jsonsms);
        }


    }




}