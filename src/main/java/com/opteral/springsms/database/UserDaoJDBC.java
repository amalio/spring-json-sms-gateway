package com.opteral.springsms.database;

import com.opteral.springsms.exceptions.GatewayException;
import com.opteral.springsms.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

@Component
public class UserDaoJDBC extends abstractDao implements UserDao  {

    private static final String GET_USER ="SELECT * FROM user WHERE name = ?";

    @Override
    public User getUserByName(String name) throws AuthenticationException {
        try {
            return getJdbcTemplate().queryForObject(GET_USER, new RowMappers.UserRowMapper(), name);
        } catch (EmptyResultDataAccessException e) {
            throw new AuthenticationCredentialsNotFoundException("no user with this name");
        }
    }

}
