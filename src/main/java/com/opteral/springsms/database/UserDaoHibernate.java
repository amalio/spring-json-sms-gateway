package com.opteral.springsms.database;

import com.opteral.springsms.model.User;
import org.springframework.security.core.AuthenticationException;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class UserDaoHibernate implements UserDao {
    @Override
    public User getUserByName(String name) throws AuthenticationException {
        throw new NotImplementedException();
    }
}
