package com.opteral.springsms.database;

import com.opteral.springsms.config.RootConfig;
import com.opteral.springsms.model.User;
import com.opteral.springsms.web.WebConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {HibernateTestConfig.class})
public class UserDaoHibernateTest {

    @Autowired
    UserDaoHibernate userDaoHibernate;

    @Test
    @Transactional
    public void getByUserName() {

        User user = userDaoHibernate.getUserByName("amalio");

        assertNotNull(user);
        assertEquals(1, user.getId());
        assertEquals("amalio", user.getName());
    }


}
