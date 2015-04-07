package com.opteral.springsms.integration;

import com.opteral.springsms.TestHelper;
import com.opteral.springsms.config.RootConfig;
import com.opteral.springsms.json.JSON_SMS;
import com.opteral.springsms.json.RequestJSON;
import com.opteral.springsms.web.WebConfig;
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
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebConfig.class, RootConfig.class})
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

        mockMvc = webAppContextSetup(this.wac)
                .apply(springSecurity())
                .build();


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
                .with(httpBasic("amalio", "secreto"))
                .contentType(TestHelper.APPLICATION_JSON_UTF8)
                .content(TestHelper.convertRequestJSONtoBytes(requestJSON)))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("response_code").value("OK"))
                .andExpect(jsonPath("$.sms_responses[0].request_ok").value(true))
                .andExpect(jsonPath("$.sms_responses[1].request_ok").value(true))

        ;

    }

    @Test
    public void testDelete() throws Exception {

        mockMvc.perform(delete("/gateway")
                .with(httpBasic("amalio", "secreto"))
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
                .with(httpBasic("amalio", "secreto"))
                .contentType(TestHelper.APPLICATION_JSON_UTF8)
                .content(TestHelper.convertRequestJSONtoBytes(requestJSON)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("msg").value(containsString("msisdn")))
        ;

    }

    @Test
    public void WithOutAuth() throws Exception {
        mockMvc.perform(post("/gateway")
                .contentType(TestHelper.APPLICATION_JSON_UTF8)
                .content(TestHelper.convertRequestJSONtoBytes(requestJSON)))
                .andExpect(status().isUnauthorized())
        ;
    }

    @Test
    public void WithBadAuth() throws Exception {
        mockMvc.perform(post("/gateway")
                .with(httpBasic("amalio", "bad password"))
                .contentType(TestHelper.APPLICATION_JSON_UTF8)
                .content(TestHelper.convertRequestJSONtoBytes(requestJSON)))
                .andExpect(status().isUnauthorized())
        ;
    }



}
