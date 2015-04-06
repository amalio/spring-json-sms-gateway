package com.opteral.springsms.validation;

import com.opteral.springsms.config.ConfigValues;
import com.opteral.springsms.exceptions.ValidationException;
import com.opteral.springsms.json.JSON_SMS;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class CheckerSMS {

    private Validator validator;

    public CheckerSMS()
    {
        validator = new ValidatorImp();
    }

    public CheckerSMS(Validator validator) {
        this.validator = validator;
    }

    public void check(List<JSON_SMS> listaJSON_SMS) throws ValidationException {

        for (JSON_SMS sms : listaJSON_SMS )
        {
            check(sms);
        }

    }

    public void check(JSON_SMS jsonsms) throws ValidationException {

        if (!isValidTexto(jsonsms))
            throw new ValidationException("Error: Valid text is needed on sms: "+jsonsms.getSubid());
        if (!isValidSender(jsonsms))
            throw new ValidationException("Error: Valid sender is needed  on sms: "+jsonsms.getSubid());
        if (!validator.isMsisdn(jsonsms.getMsisdn()))
            throw new ValidationException("Error: Valid msisdn is needed  on sms: "+jsonsms.getSubid());
        if (!isValidAck(jsonsms))
            throw new ValidationException("Error: ACK configuration issue  on sms: "+jsonsms.getSubid());
        if (!isValidSubid(jsonsms.getSubid()))
            throw new ValidationException("Error: subid size must be lower than "+ ConfigValues.SUBID_MAX_SIZE+ " on sms: "+jsonsms.getSubid());
        if (!isValidDateTime(jsonsms.getDatetime()))
            throw new ValidationException("Error:  The scheduled date can not be earlier than the current date, on sms: "+jsonsms.getSubid());


    }

    private boolean isValidDateTime(Date datetime)
    {
        if (datetime == null)
            return true;
        else
        {

            if (datetime.before(new Date()))
                return false;
            else
                return true;
        }
    }

    private boolean isValidSubid(String subid)
    {
        if (subid == null)
            return true;
        else
        {
            if (subid.length() > ConfigValues.SUBID_MAX_SIZE)
                return false;
            else
                return true;
        }
    }

    private boolean isValidTexto(JSON_SMS jsonsms)
    {

        String texto = jsonsms.getText();

        return !(texto == null || texto.isEmpty() || texto.length() > ConfigValues.MAX_SMS_SIZE);


    }

    private boolean isValidSender(JSON_SMS json_sms)
    {
        if (json_sms.getSender() == null)
            return false;

        return (json_sms.getSender().length() > 0 && json_sms.getSender().length() < ConfigValues.SENDER_MAX_SIZE);

    }

    private boolean isValidAck(JSON_SMS jsonsms)
    {
        if (jsonsms.getAck_url() == null)
            return true;

        if (jsonsms.getAck_url().isEmpty())
        {
            return true;
        }
        else
        {
            if (jsonsms.getSubid() == null || jsonsms.getSubid().isEmpty())
            {
                return false;
            }
            else
            {
                try
                {
                    return validator.isURL(jsonsms.getAck_url());
                }
                catch (ValidationException e) {
                    return false;
                }


            }
        }

    }




}
