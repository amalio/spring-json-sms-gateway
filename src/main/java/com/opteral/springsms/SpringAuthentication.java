package com.opteral.springsms;


import com.opteral.springsms.model.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

@Component
public class SpringAuthentication {

    public int getUserId()
    {
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getId();
    }
}
