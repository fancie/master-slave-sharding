#使用JPA+Shardingsphere进行读写分离+分库分表的DEMO
-------------------

项目支持任意多个套主从（带分表），写主库，查从库

1.设置主从配置
```
        MasterSlaveRuleConfiguration masterSlaveRuleConfiguration = new MasterSlaveRuleConfiguration("ds", "database0", Arrays.asList("database1"));
        shardingRuleConfig.setMasterSlaveRuleConfigs(Arrays.asList(masterSlaveRuleConfiguration));
```

2.设置库和表的路由规则
```
    private TableRuleConfiguration getTableRuleConfiguration() {
        TableRuleConfiguration tableRuleConfiguration = new TableRuleConfiguration(logicTable, actualDataNodes);
        //数据库的选择是用MasterSlaveRuleConfiguration的名称加编号
        tableRuleConfiguration.setDatabaseShardingStrategyConfig(new InlineShardingStrategyConfiguration("goods_type", "ds"));
        tableRuleConfiguration.setTableShardingStrategyConfig(new StandardShardingStrategyConfiguration(shardingColumn, preciseAlgorithm));
        return tableRuleConfiguration;
    }
```
其它的请下载代码自行查阅

演示：
------

插入数据，所有数据都写入了主库

http://localhost:8080/save

查询，查询时从库差出来的（可以设置一条只有从库存在的记录来验证）

http://localhost:8080/getone

--------
fancie 2022-02 杭州
