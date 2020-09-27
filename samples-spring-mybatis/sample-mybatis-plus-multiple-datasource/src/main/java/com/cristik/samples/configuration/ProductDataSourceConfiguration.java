package com.cristik.samples.configuration;

import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * @author zhenghua.ni
 */
@Configuration
@MapperScan(basePackages = "com.cristik.samples.mapper.product", sqlSessionFactoryRef = "productSqlSessionFactory"
        , sqlSessionTemplateRef = "productSqlSessionTemplate")
public class ProductDataSourceConfiguration {

    @Bean(name = "productDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.product")
    public DataSource productDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean(name = "productSqlSessionFactory")
    public SqlSessionFactory productSqlSessionFactory() throws Exception {
        MybatisSqlSessionFactoryBean mybatisSqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
        mybatisSqlSessionFactoryBean.setDataSource(productDataSource());
        Resource[] resources = new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/product/*.xml");
        mybatisSqlSessionFactoryBean.setMapperLocations(resources);
        return mybatisSqlSessionFactoryBean.getObject();
    }

    @Bean(name = "productTransactionManager")
    public DataSourceTransactionManager productTransactionManager() {
        return new DataSourceTransactionManager(productDataSource());
    }

    @Bean(name = "productSqlSessionTemplate")
    public SqlSessionTemplate productSqlSessionTemplate() throws Exception {
        return new SqlSessionTemplate(productSqlSessionFactory());
    }
}
