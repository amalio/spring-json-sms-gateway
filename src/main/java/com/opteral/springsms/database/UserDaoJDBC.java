package com.opteral.springsms.database;

import com.opteral.springsms.model.User;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

@Component
public class UserDaoJDBC implements UserDao {
    @Override
    public User getUserByName(String name) throws AuthenticationException {
        throw new NotImplementedException();
    }
}
