package com.opteral.springsms.database;


import com.opteral.springsms.model.User;
import org.springframework.security.core.AuthenticationException;

public interface UserDao {
    User getUserByName(String name) throws AuthenticationException;
}
