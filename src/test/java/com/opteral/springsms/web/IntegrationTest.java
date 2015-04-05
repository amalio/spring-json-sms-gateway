package com.opteral.springsms.web;

import com.opteral.springsms.TestHelper;
import com.opteral.springsms.json.JSON_SMS;
import com.opteral.springsms.json.RequestJSON;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = WebConfig.class)
@ActiveProfiles("test")
public class IntegrationTest {
    private MockMvc mockMvc;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    protected WebApplicationContext wac;


    private RequestJSON requestJSON = new RequestJSON();


    @Before
    public void setup() {

        MockitoAnnotations.initMocks(this);

        mockMvc = webAppContextSetup(this.wac).build();

        requestJSON.setUser("user");
        requestJSON.setPassword("password");
        JSON_SMS json_sms = new JSON_SMS();
        json_sms.setMsisdn("34646974525");
        json_sms.setSender("sender");
        json_sms.setText("message test");
        requestJSON.addSMS(json_sms);
        requestJSON.addSMS(json_sms);
    }

    @Test
    public void testPost() throws Exception {

        mockMvc.perform(post("/gateway")
                .contentType(TestHelper.APPLICATION_JSON_UTF8)
                .content(TestHelper.convertRequestJSONtoBytes(requestJSON)))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("response_code").value("OK"))
        ;

    }

    @Test
    public void checkFails() throws Exception {

        requestJSON.getSms_request().get(0).setMsisdn("not a msisdn");

        mockMvc.perform(post("/gateway")
                .contentType(TestHelper.APPLICATION_JSON_UTF8)
                .content(TestHelper.convertRequestJSONtoBytes(requestJSON)))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("msg").value(containsString("msisdn")))
        ;

    }



}
