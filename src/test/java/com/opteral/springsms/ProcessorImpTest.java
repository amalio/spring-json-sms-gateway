package com.opteral.springsms;

import com.opteral.springsms.database.SmsDao;
import com.opteral.springsms.exceptions.GatewayException;
import com.opteral.springsms.exceptions.ValidationException;
import com.opteral.springsms.json.JSON_SMS;
import com.opteral.springsms.json.RequestJSON;
import com.opteral.springsms.json.ResponseJSON;
import com.opteral.springsms.model.SMS;
import com.opteral.springsms.model.User;
import com.opteral.springsms.validation.CheckerSMS;
import com.opteral.springsms.web.WebAuthentication;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class ProcessorImpTest {

    private CheckerSMS checkerSMSMock;
    private ProcessorImp processService;
    private RequestJSON requestJSON;
    private SmsDao smsDaoMock;
    private WebAuthentication authenticationMock;
    private User user;

    @Before
    public void init()
    {
        user = new User();
        user.setId(10);

        requestJSON = new RequestJSON();
        JSON_SMS jsonsms1 = new JSON_SMS();
        JSON_SMS jsonsms2 = new JSON_SMS();
        requestJSON.getSms_request().add(jsonsms1);
        requestJSON.getSms_request().add(jsonsms2);

        checkerSMSMock = mock(CheckerSMS.class);
        smsDaoMock = mock(SmsDao.class);
        authenticationMock = mock(WebAuthentication.class);
        processService = new ProcessorImp(checkerSMSMock, smsDaoMock, authenticationMock);

    }

    @Test  (expected = ValidationException.class)
    public void checkFails() throws GatewayException {
        doThrow(new ValidationException("validation fails")).when(checkerSMSMock).check(anyListOf(JSON_SMS.class));

        processService.post(new RequestJSON());
    }

    @Test
    public void requestSMSNewsOk() throws GatewayException {

        when(authenticationMock.getUser()).thenReturn(user);

        ResponseJSON responseJSON = processService.post(requestJSON);


        assertEquals(ResponseJSON.ResponseCode.OK, responseJSON.getResponse_code());
        assertTrue(responseJSON.getSms_responses().get(0).isRequest_ok());
        assertTrue(responseJSON.getSms_responses().get(1).isRequest_ok());
        verify(checkerSMSMock).check(anyListOf(JSON_SMS.class));

        ArgumentCaptor<SMS> argument = ArgumentCaptor.forClass(SMS.class);
        verify(smsDaoMock, times(2)).insert(argument.capture());
        assertEquals(10, argument.getValue().getUser_id());



    }

    @Test
    public void requestSMSUpdateOk() throws GatewayException {
        requestJSON.getSms_request().get(0).setId(500);
        requestJSON.getSms_request().get(1).setId(501);
        when(authenticationMock.getUser()).thenReturn(user);

        ResponseJSON responseJSON = processService.post(requestJSON);


        assertEquals(ResponseJSON.ResponseCode.OK, responseJSON.getResponse_code());
        assertTrue(responseJSON.getSms_responses().get(0).isRequest_ok());
        assertTrue(responseJSON.getSms_responses().get(1).isRequest_ok());
        verify(checkerSMSMock).check(anyListOf(JSON_SMS.class));

        ArgumentCaptor<SMS> argument = ArgumentCaptor.forClass(SMS.class);
        verify(smsDaoMock, times(2)).update(argument.capture());
        assertEquals(10, argument.getValue().getUser_id());



    }

    @Test
    public void smsDAOFails() throws GatewayException {
        when(authenticationMock.getUser()).thenReturn(user);
        doThrow(new GatewayException("msg")).when(smsDaoMock).insert(any(SMS.class));
        doThrow(new GatewayException("msg")).when(smsDaoMock).update(any(SMS.class));

        ResponseJSON responseJSON = processService.post(requestJSON);

        assertEquals(ResponseJSON.ResponseCode.OK, responseJSON.getResponse_code());
        assertFalse(responseJSON.getSms_responses().get(0).isRequest_ok());
        assertFalse(responseJSON.getSms_responses().get(1).isRequest_ok());

        verify(checkerSMSMock).check(anyListOf(JSON_SMS.class));
        verify(smsDaoMock, times(2)).insert(any(SMS.class));


    }

    @Test
    public void noPersistIfTestField() throws GatewayException {

        requestJSON.getSms_request().get(0).setTest(true);
        requestJSON.getSms_request().get(1).setTest(true);
        when(authenticationMock.getUser()).thenReturn(user);

        ResponseJSON responseJSON = processService.post(requestJSON);

        assertEquals(ResponseJSON.ResponseCode.OK, responseJSON.getResponse_code());
        assertTrue(responseJSON.getSms_responses().get(0).isRequest_ok());
        assertTrue(responseJSON.getSms_responses().get(1).isRequest_ok());

        verify(checkerSMSMock).check(anyListOf(JSON_SMS.class));
        verify(smsDaoMock, times(0)).insert(any(SMS.class));
        verify(smsDaoMock, times(0)).update(any(SMS.class));


    }

    @Test
    public void authenticationFailsRetrievingId() throws GatewayException {

        ResponseJSON responseJSON = processService.post(requestJSON);

        assertEquals(ResponseJSON.ResponseCode.OK, responseJSON.getResponse_code());
        assertFalse(responseJSON.getSms_responses().get(0).isRequest_ok());
        assertFalse(responseJSON.getSms_responses().get(1).isRequest_ok());

        verify(checkerSMSMock).check(anyListOf(JSON_SMS.class));
        verify(smsDaoMock, times(0)).insert(any(SMS.class));
        verify(smsDaoMock, times(0)).update(any(SMS.class));


    }

    @Test
    public void requestForDeleteOk() throws GatewayException {

        when(authenticationMock.getUser()).thenReturn(user);

        ResponseJSON responseJSON = processService.delete(requestJSON);

        assertEquals(ResponseJSON.ResponseCode.OK, responseJSON.getResponse_code());
        assertTrue(responseJSON.getSms_responses().get(0).isRequest_ok());
        assertTrue(responseJSON.getSms_responses().get(1).isRequest_ok());
        verify(checkerSMSMock, times(0)).check(anyListOf(JSON_SMS.class));

        ArgumentCaptor<SMS> argument = ArgumentCaptor.forClass(SMS.class);
        verify(smsDaoMock, times(2)).delete(argument.capture());
        assertEquals(10, argument.getValue().getUser_id());



    }
}
