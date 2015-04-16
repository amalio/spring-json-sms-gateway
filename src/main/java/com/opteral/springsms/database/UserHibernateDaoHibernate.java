package com.opteral.springsms.database;

import com.opteral.springsms.model.User;
import org.hibernate.criterion.Restrictions;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;


@Component
public class UserHibernateDaoHibernate extends AbstractHibernateDao implements UserDao {


    @Override
    public User getUserByName(String name) throws AuthenticationException {
        try {
            return (User) currentSession()
                    .createCriteria(User.class)
                    .add(Restrictions.eq("name", name))
                    .list().get(0);
        } catch (Exception e) {
            throw new AuthenticationCredentialsNotFoundException("no user with this name");
        }
    }
}
