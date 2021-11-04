package com.crisik.sample.sharding.configuration;

import com.crisik.sample.sharding.algorithm.ModuloShardingTableAlgorithm;
import com.crisik.sample.sharding.utils.DataSourceUtil;
import org.apache.shardingsphere.api.config.sharding.KeyGeneratorConfiguration;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.InlineShardingStrategyConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.StandardShardingStrategyConfiguration;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author cristik
 */
@Configuration
public class ShardingJdbcConfiguration {

    @Bean
    public DataSource shardingDataSource() throws SQLException {
        ShardingRuleConfiguration shardingRuleConfiguration = new ShardingRuleConfiguration();
        shardingRuleConfiguration.getTableRuleConfigs().add(orderTableRuleConfiguration());
        shardingRuleConfiguration.getTableRuleConfigs().add(orderItemTableRuleConfiguration());
        shardingRuleConfiguration.getBindingTableGroups().add("t_order, t_order_item");
        shardingRuleConfiguration.getBroadcastTables().add("t_config");
        shardingRuleConfiguration.setDefaultDatabaseShardingStrategyConfig(new InlineShardingStrategyConfiguration("user_id", "ds${user_id % 2}"));
        shardingRuleConfiguration.setDefaultTableShardingStrategyConfig(new StandardShardingStrategyConfiguration("order_id", new ModuloShardingTableAlgorithm()));
        return ShardingDataSourceFactory.createDataSource(createDataSourceMap(), shardingRuleConfiguration, new Properties());
    }

    TableRuleConfiguration orderTableRuleConfiguration() {
        TableRuleConfiguration tableRuleConfiguration = new TableRuleConfiguration("t_order", "ds${0..1}.t_order${0..1}");
        tableRuleConfiguration.setKeyGeneratorConfig(new KeyGeneratorConfiguration("SNOWFLAKE", "order_id"));
        return tableRuleConfiguration;
    }

    TableRuleConfiguration orderItemTableRuleConfiguration() {
        return new TableRuleConfiguration("t_order_item", "ds${0..1}.t_order_item${0..1}");
    }

    Map<String, DataSource> createDataSourceMap() {
        Map<String, DataSource> result = new HashMap<>();
        result.put("ds0", DataSourceUtil.createDataSource("ds0"));
        result.put("ds1", DataSourceUtil.createDataSource("ds1"));
        return result;
    }


}
