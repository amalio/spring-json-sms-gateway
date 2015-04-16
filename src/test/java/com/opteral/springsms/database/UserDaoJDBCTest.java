package com.opteral.springsms.database;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.dataset.ReplacementDataSetLoader;
import com.opteral.springsms.config.RootConfig;
import com.opteral.springsms.model.User;
import com.opteral.springsms.web.WebConfig;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.sql.DataSource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebConfig.class, RootConfig.class})
@ActiveProfiles("test")
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class, DirtiesContextTestExecutionListener.class })
@DbUnitConfiguration(dataSetLoader = ReplacementDataSetLoader.class)
@DatabaseTearDown("/dataset/user.xml")
public class UserDaoJDBCTest {

    @Autowired
    @Qualifier("dataSource")
    private DataSource dataSource;

    @Autowired
    private UserDaoJDBC userDaoJDBC;

    @Test
    @DatabaseSetup("/dataset/user.xml")
    public void getSMSTest() throws Exception {

        User user = userDaoJDBC.getUserByName("amalio");

        assertNotNull(user);
        assertEquals(1, user.getId());
        assertEquals("amalio", user.getName());

    }

}
