package com.opteral.springsms.validation;


import com.opteral.springsms.exceptions.ValidationException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class ValidatorMsisdnTest {




    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{

                {true, "34618984103"},
                {false, "+34618984103"},
                {false, "45618984103"},
                {false, "618425648"},
                {false, "346184256485"}

        });
    }

    @Parameterized.Parameter
    public boolean expected;

    @Parameterized.Parameter (value = 1)
    public String msisdn;



    @Test
    public void test() throws ValidationException {

        Validator validator = new ValidatorImp();

        assertEquals(expected, validator.isMsisdn(msisdn));
    }




}