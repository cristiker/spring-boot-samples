package com.crisik.sample.sharding.algorithm;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Range;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;

import java.util.ArrayList;
import java.util.Collection;

@Slf4j
public class ModuloShardingDatabaseAlgorithm implements PreciseShardingAlgorithm<Long>, RangeShardingAlgorithm<Long> {

    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Long> shardingValue) {
        log.info("databaseNames:{}", JSON.toJSONString(availableTargetNames));
        log.info("shardingValue:{}", JSON.toJSONString(shardingValue));

        String databaseName = "";

        // 只做单库分表
        if ("t_user".equalsIgnoreCase(shardingValue.getLogicTableName())) {
            databaseName = (String) availableTargetNames.toArray()[0];
        }
        // 分库分表
        if ("t_order".equalsIgnoreCase(shardingValue.getLogicTableName()) ||
                "t_order_item".equalsIgnoreCase(shardingValue.getLogicTableName())) {
            for (String each : availableTargetNames) {
                if (each.endsWith(shardingValue.getValue() % availableTargetNames.size() + "")) {
                    databaseName = each;
                    break;
                }
            }
        }
        log.info("databaseName:{}", databaseName);
        if (StringUtils.isNotEmpty(databaseName)) {
            return databaseName;
        }
        throw new UnsupportedOperationException();
    }

    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, RangeShardingValue<Long> shardingValue) {
        log.info("collection:{}", JSON.toJSONString(availableTargetNames));
        log.info("rangeShardingValue:{}", JSON.toJSONString(availableTargetNames));

        Collection<String> collect = new ArrayList<>();
        Range<Long> valueRange = shardingValue.getValueRange();

        log.info("valueRange:{}", JSON.toJSONString(valueRange));
        log.info("lowerEndpoint:{}", valueRange.lowerEndpoint());
        log.info("upperEndpoint:{}", valueRange.upperEndpoint());


        for (Long i = valueRange.lowerEndpoint(); i <= valueRange.upperEndpoint(); i++) {
            for (String each : availableTargetNames) {
                if (each.endsWith(i % availableTargetNames.size() + "")) {
                    collect.add(each);
                }
            }
        }

        log.info("collect:{}", JSON.toJSONString(collect));

        return collect;
    }
}
