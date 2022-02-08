package com.hidiu.sharding.config;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * @author fancie
 * @title: PreciseAlgorithm
 * @projectName multi-sharding
 * @description: TODO
 * @date 2022/2/7 上午11:19
 */
@Component
public class PreciseAlgorithm implements PreciseShardingAlgorithm<Long> {
    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<Long> preciseShardingValue) {
        long id = preciseShardingValue.getValue();
        if ((id & 1) == 1){
            return (String) collection.toArray()[0];
        }else {
            return (String) collection.toArray()[1];
        }
    }
}
