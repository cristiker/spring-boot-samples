package com.cristik.sample.sharding.configuration;

import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.zaxxer.hikari.HikariDataSource;
import io.shardingsphere.shardingjdbc.api.MasterSlaveDataSourceFactory;
import io.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import io.shardingsphere.shardingjdbc.spring.boot.masterslave.SpringBootMasterSlaveRuleConfigurationProperties;
import io.shardingsphere.shardingjdbc.spring.boot.sharding.SpringBootShardingRuleConfigurationProperties;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author zhenghua.ni
 */
@Configuration
@MapperScan(basePackages = {"com.cristik.sample.sharding.mapper"}, sqlSessionFactoryRef = "sqlSessionFactory"
        , sqlSessionTemplateRef = "sqlSessionTemplate")
public class MybatisPlusConfiguration {

    @Autowired
    private SpringBootShardingRuleConfigurationProperties shardingProperties;

    @Autowired
    private SpringBootMasterSlaveRuleConfigurationProperties masterSlaveProperties;

    @Primary
    @Bean(name = "dataSource")
    @ConfigurationProperties(prefix = "sharding.jdbc.datasource.sample1")
    public DataSource dataSource() {
        System.out.println(1);
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean(name = "shardingDataSource")
    public DataSource shardingDataSource() throws SQLException {
        Map<String, DataSource> dataSourceMap = new LinkedHashMap<>();
        dataSourceMap.put("sample1", dataSource());
        if (StringUtils.isBlank(masterSlaveProperties.getMasterDataSourceName())) {
            return ShardingDataSourceFactory.createDataSource(dataSourceMap, shardingProperties.getShardingRuleConfiguration()
                    , shardingProperties.getConfigMap(), shardingProperties.getProps());
        } else {
            return MasterSlaveDataSourceFactory.createDataSource(dataSourceMap, masterSlaveProperties.getMasterSlaveRuleConfiguration()
                    , masterSlaveProperties.getConfigMap(), masterSlaveProperties.getProps());
        }
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        MybatisSqlSessionFactoryBean bean = new MybatisSqlSessionFactoryBean();
        bean.setDataSource(shardingDataSource());
        Resource[] resources = new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/sharding/*.xml");
        //对应mapper.xml的具体位置
        bean.setMapperLocations(resources);
        return bean.getObject();
    }

    @Bean
    public DataSourceTransactionManager transactionManager() throws SQLException {
        return new DataSourceTransactionManager(shardingDataSource());
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
