package com.opteral.springsms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

@Configuration
public class ConfigProvider {

    @Bean(name="config_input")
    @Profile("test")
    public InputStream dataSource() {
        return ConfigProvider.class.getClassLoader().getResourceAsStream("gateway.properties");
    }

    @Bean(name="config_input")
    @Profile("default")
    public InputStream embeddedDataSource() throws FileNotFoundException {
        return new FileInputStream("etc/amalio/gateway.properties");
    }

}
