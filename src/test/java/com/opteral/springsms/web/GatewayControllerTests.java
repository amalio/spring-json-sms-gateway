package com.opteral.springsms.web;

import com.opteral.springsms.ProcessService;
import com.opteral.springsms.TestHelper;
import com.opteral.springsms.exceptions.GatewayException;;
import com.opteral.springsms.json.JSON_SMS;
import com.opteral.springsms.json.RequestJSON;
import com.opteral.springsms.json.ResponseJSON;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.Profile;
import org.springframework.context.support.StaticApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

import javax.naming.AuthenticationException;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = WebConfig.class)
@ActiveProfiles("test")
public class GatewayControllerTests {

    private MockMvc mockMvc;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    protected WebApplicationContext wac;

    @Mock
    ProcessService processServiceMock;

    @InjectMocks
    GatewayController gatewayController;

    private RequestJSON requestJSON = new RequestJSON();



    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        final ExceptionHandlerExceptionResolver exceptionHandlerExceptionResolver = new ExceptionHandlerExceptionResolver();
        final StaticApplicationContext applicationContext = new StaticApplicationContext();
        applicationContext.registerBeanDefinition("advice", new RootBeanDefinition(GlobalErrorHandler.class, null, null));
        exceptionHandlerExceptionResolver.setApplicationContext(applicationContext);
        exceptionHandlerExceptionResolver.afterPropertiesSet();

        mockMvc = MockMvcBuilders.standaloneSetup(gatewayController).setHandlerExceptionResolvers(exceptionHandlerExceptionResolver).build();


        JSON_SMS json_sms = new JSON_SMS();
        json_sms.setMsisdn("34646974525");
        json_sms.setSender("sender");
        json_sms.setText("message test");
        requestJSON.addSMS(json_sms);
        requestJSON.addSMS(json_sms);
    }


    @Test
    public void gatewayExceptionTest() throws Exception {
        doThrow(new GatewayException("general error")).when(processServiceMock).process(any(RequestJSON.class));
        mockMvc.perform(post("/gateway")
                .contentType(TestHelper.APPLICATION_JSON_UTF8)
                .content(TestHelper.convertRequestJSONtoBytes(requestJSON)))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("response_code").value("ERROR_GENERAL"))
                .andExpect(jsonPath("msg").value("general error"))
        ;
    }

    @Test
    public void otherExceptionTest() throws Exception {
        doThrow(new RuntimeException()).when(processServiceMock).process(any(RequestJSON.class));
        mockMvc.perform(post("/gateway")
                .contentType(TestHelper.APPLICATION_JSON_UTF8)
                .content(TestHelper.convertRequestJSONtoBytes(requestJSON)))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("response_code").value("ERROR_GENERAL"))
        ;
    }
}
