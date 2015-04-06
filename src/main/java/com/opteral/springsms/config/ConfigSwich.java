package com.opteral.springsms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
public class ConfigSwich {
    @Bean(name="config_swich")
    @Profile("test")
    public boolean swichTest() {
        return true;
    }

    @Bean(name="config_swich")
    @Profile("default")
    public boolean swichDefault(){
        return false;
    }
}
