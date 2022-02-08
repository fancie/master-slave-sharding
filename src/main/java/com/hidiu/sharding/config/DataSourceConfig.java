package com.hidiu.sharding.config;

import org.apache.shardingsphere.api.config.masterslave.MasterSlaveRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.InlineShardingStrategyConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.StandardShardingStrategyConfiguration;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.*;

/**
 * @author fancie
 * @title: DataSourceConfig
 * @projectName multi-sharding
 * @description: TODO
 * @date 2022/2/7 上午11:15
 */
@Configuration
public class DataSourceConfig {

    @Autowired
    private Database0Config database0Config;

    @Autowired
    private Database1Config database1Config;

    @Autowired
    private PreciseAlgorithm preciseAlgorithm;

    @Value("${spring.sharding.tables.goods.logicTable}")
    private String logicTable;

    @Value("${spring.sharding.tables.goods.tableStrategy.standard.shardingColumn}")
    private String shardingColumn;


    @Value("${spring.sharding.tables.goods.actualDataNodes}")
    private String actualDataNodes;

    @Bean
    public DataSource getDataSource() throws SQLException {
        return buildDataSource();
    }

    //分库设置，如果不设置分库就只需put一个
    private Map<String, DataSource> getDataSourceMap() {
        Map<String, DataSource> dataSourceMap = new HashMap<>(2);
        //添加两个数据库database0和database1 组合成一套主从
        dataSourceMap.put(database0Config.getDatabaseName(), database0Config.createDataSource());
        dataSourceMap.put(database1Config.getDatabaseName(), database1Config.createDataSource());
        return dataSourceMap;
    }

    private TableRuleConfiguration getTableRuleConfiguration() {
        TableRuleConfiguration tableRuleConfiguration = new TableRuleConfiguration(logicTable, actualDataNodes);
        //数据库的选择是用MasterSlaveRuleConfiguration的名称加编号
        tableRuleConfiguration.setDatabaseShardingStrategyConfig(new InlineShardingStrategyConfiguration("goods_type", "ds"));
        tableRuleConfiguration.setTableShardingStrategyConfig(new StandardShardingStrategyConfiguration(shardingColumn, preciseAlgorithm));
        return tableRuleConfiguration;
    }


    private DataSource buildDataSource() throws SQLException {
        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
        shardingRuleConfig.getBindingTableGroups().add(logicTable);
        //配置goods表规则
        shardingRuleConfig.getTableRuleConfigs().add(getTableRuleConfiguration());
        shardingRuleConfig.setDefaultDatabaseShardingStrategyConfig(new InlineShardingStrategyConfiguration("goods_type", "ds"));

        MasterSlaveRuleConfiguration masterSlaveRuleConfiguration = new MasterSlaveRuleConfiguration("ds", "database0", Arrays.asList("database1"));
        shardingRuleConfig.setMasterSlaveRuleConfigs(Arrays.asList(masterSlaveRuleConfiguration));
        return ShardingDataSourceFactory.createDataSource(getDataSourceMap(), shardingRuleConfig, new Properties());
    }

}
