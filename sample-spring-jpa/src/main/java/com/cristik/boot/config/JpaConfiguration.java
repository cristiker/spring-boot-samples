package com.cristik.boot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

/**
 * @author cristik
 */

@Configuration
public class JpaConfiguration {

//    @Bean
//    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
//        JpaTransactionManager transactionManager = new JpaTransactionManager();
//        transactionManager.setEntityManagerFactory(emf);
//        return transactionManager;
//    }
//
//
//    @Bean
//    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
//        LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean =
//                new LocalContainerEntityManagerFactoryBean();
//        localContainerEntityManagerFactoryBean.setDataSource(dataSource);
//        localContainerEntityManagerFactoryBean.setPackagesToScan(new String[]{"com.cristik.boot.application.jpa.entity"});
//        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
//        localContainerEntityManagerFactoryBean.setJpaVendorAdapter(vendorAdapter);
//        return localContainerEntityManagerFactoryBean;
//    }

}
