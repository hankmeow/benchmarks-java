package com.hankmew.benchmarks.middleware.mysql.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

@Slf4j
public class DynamicDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() { //所有的请求都会走此处，所以没有切换的时候，不要输出日志吧
        DynamicDataSourceEnum dataSourceEnum = DynamicDataSourceContextHolder.getDataSourceId();
        log.info("线程[{}]，此时切换到的数据源为:{}", Thread.currentThread().getId(), dataSourceEnum);
        return dataSourceEnum;
    }
}
