package com.opteral.springsms.database;

import com.opteral.springsms.model.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.inject.Inject;


@Component
public class UserDaoHibernate implements UserDao {

    private SessionFactory sessionFactory;

    @Inject
    public UserDaoHibernate(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private Session currentSession() {
        return sessionFactory.getCurrentSession();
    }

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
