package com.opteral.springsms;

import com.opteral.springsms.exceptions.GatewayException;
import com.opteral.springsms.exceptions.ValidationException;
import com.opteral.springsms.json.JSON_SMS;
import com.opteral.springsms.json.RequestJSON;
import com.opteral.springsms.validation.CheckerSMS;
import com.opteral.springsms.validation.ValidatorImp;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ProcessServiceImpTest {

    private CheckerSMS checkerSMSMock;
    private ProcessServiceImp processService;

    @Before
    public void init()
    {
        checkerSMSMock = mock(CheckerSMS.class);
        processService = new ProcessServiceImp(checkerSMSMock);

    }

    @Test  (expected = ValidationException.class)
    public void checkFails() throws GatewayException {
        doThrow(new ValidationException("validation fails")).when(checkerSMSMock).check(anyListOf(JSON_SMS.class));

        processService.process(new RequestJSON());
    }
}
