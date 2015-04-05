package com.opteral.springsms.validation;

import com.opteral.springsms.exceptions.ValidationException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class ValidatorURLTest {




    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{

                {true, "http://www.web.com"},
                {true, "https://www.web.com/ack.php"},
                {false, "https://www."},
                {false, "ddsa.com"},
                {false, "dsadsadsa"},
                {false, ".dsadsa"},
                {false, "dsadsa."}

        });
    }

    @Parameterized.Parameter
    public boolean expected;

    @Parameterized.Parameter (value = 1)
    public String url;



    @Test
    public void test() throws ValidationException {

        Validator validator = new ValidatorImp();

        assertEquals(expected, validator.isURL(url));
    }




}
