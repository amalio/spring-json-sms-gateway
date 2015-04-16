package com.opteral.springsms.database;

import com.opteral.springsms.config.DataConfig;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;


@Configuration
@EnableTransactionManagement
public class HibernateTestConfig implements TransactionManagementConfigurer {

    @Autowired
    private SessionFactory sessionFactory;
    public PlatformTransactionManager annotationDrivenTransactionManager() {

        System.out.println(sessionFactory);
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory);
        return transactionManager;

    }



    /*
    @Bean
    public DataSource dataSource() {
        EmbeddedDatabaseBuilder edb = new EmbeddedDatabaseBuilder();
        edb.setType(EmbeddedDatabaseType.H2);
        edb.addScript("classpath:schema.sql");
         edb.addScript("classpath:test-data.sql");
        EmbeddedDatabase embeddedDatabase = edb.build();
        return embeddedDatabase;
    }
    */




}