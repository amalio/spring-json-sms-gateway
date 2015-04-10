package com.opteral.springsms.config;

import com.opteral.springsms.Utilities;
import com.opteral.springsms.sender.ACKSender;
import com.opteral.springsms.sender.Sender;
import com.opteral.springsms.sender.SenderContext;
import com.opteral.springsms.smsc.*;
import org.apache.log4j.Logger;
import org.jsmpp.session.SMPPSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.*;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.annotation.PostConstruct;

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass=true)
@ComponentScan(basePackages={"com.opteral.springsms"},
    excludeFilters={
        @Filter(type=FilterType.ANNOTATION, value=EnableWebMvc.class)
    })
public class RootConfig {

    @Bean
    public LoggerAOP loggerAOP(){
        return new LoggerAOP();
    }

    @Bean
    public SMPPSession sMPPSession(){
        return new SMPPSession();
    }

    @Autowired
    Environment env;

    @PostConstruct
    public void init()
    {
        try {
            Utilities.getConfig(isTest());
        }
        catch (Exception e)
        {
            throw new RuntimeException("Failed loading configuration file");
        }
    }

    private boolean isTest()
    {
        String[] profiles = env.getActiveProfiles();
        for (String profile : profiles){
            if (profile.equals("test"))
                return true;
        }
        return false;
    }
}
