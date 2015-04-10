package com.opteral.springsms.web;


import com.opteral.springsms.database.UserDao;
import com.opteral.springsms.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

@Component
public class WebAuthentication {

    @Autowired
    UserDao userDao;

    public User getUser() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        return userDao.getUserByName(name);
    }

}
