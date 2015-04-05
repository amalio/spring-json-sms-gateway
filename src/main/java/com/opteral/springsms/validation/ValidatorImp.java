package com.opteral.springsms.validation;


import com.opteral.springsms.exceptions.ValidationException;
import org.apache.commons.validator.UrlValidator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidatorImp implements Validator {

    private Pattern msisdn_pattern;

    private String[] schemes = {"http","https"};

    private UrlValidator urlValidator = new UrlValidator(schemes);


    private static final String MSISDN_PATTERN = "^34\\d{9}$";


    public ValidatorImp() {

        this.msisdn_pattern = Pattern.compile(MSISDN_PATTERN);
    }


    @Override
    public boolean isURL(String url) {

        return urlValidator.isValid(url);
    }

    @Override
    public boolean isMsisdn(String cadena) throws ValidationException {

        Matcher matcher = msisdn_pattern.matcher(cadena);
        return matcher.matches();

    }
}
