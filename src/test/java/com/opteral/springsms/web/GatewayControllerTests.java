package com.opteral.springsms.web;

import com.opteral.springsms.ProcessService;
import com.opteral.springsms.exceptions.LoginException;
import com.opteral.springsms.json.ResponseJSON;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = WebConfig.class)
public class GatewayControllerTests {

    private MockMvc mockMvc;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    protected WebApplicationContext wac;

    @Mock
    ProcessService processServiceMock;

    @InjectMocks
    GatewayController gatewayController;



    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        // mockMvc = webAppContextSetup(wac).build();
        mockMvc = MockMvcBuilders.standaloneSetup(gatewayController).setHandlerExceptionResolvers(new ExceptionHandlerExceptionResolver()).build();
    }


    @Test
    public void testResponseControllerIsJSON() throws Exception {
        when(processServiceMock.process()).thenReturn(new ResponseJSON(ResponseJSON.ResponseCode.OK, "ok"));
        mockMvc.perform(get("/gateway"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("response_code").value("OK"))
                ;
    }

    @Test
    @Ignore
    public void loginExceptionTest() throws Exception {
        doThrow(new LoginException("login error")).when(processServiceMock).process();
        mockMvc.perform(get("/gateway"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("response_code").value("ERROR_LOGIN"))
        ;
    }


}
