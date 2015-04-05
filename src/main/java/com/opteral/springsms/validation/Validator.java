package com.opteral.springsms.validation;


import com.opteral.springsms.exceptions.ValidationException;

public interface Validator {

    boolean isURL(final String cadena) throws ValidationException;
    boolean isMsisdn(final String cadena) throws ValidationException;
}
