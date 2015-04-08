package com.opteral.springsms;

import org.springframework.stereotype.Component;

import javax.ws.rs.client.ClientBuilder;

@Component
public class HttpGetSender {

    public void sendGet(String url)  {

        ClientBuilder.newClient().target(url).request().async().get();

    }
}
