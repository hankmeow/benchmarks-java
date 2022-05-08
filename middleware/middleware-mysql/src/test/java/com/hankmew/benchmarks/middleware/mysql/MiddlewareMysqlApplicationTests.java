package com.hankmew.benchmarks.middleware.mysql;

import com.hankmew.benchmarks.middleware.mysql.config.DynamicDataSourceContextHolder;
import com.hankmew.benchmarks.middleware.mysql.config.DynamicDataSourceEnum;
import com.hankmew.benchmarks.middleware.mysql.mapper.KVConfigMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@Slf4j
@SpringBootTest
class MiddlewareMysqlApplicationTests {
    @Resource
    KVConfigMapper kvConfigMapper;

    @Test
    void contextLoads() {
        DynamicDataSourceContextHolder.setDataSourceId(DynamicDataSourceEnum.SHARD0);
        String a = kvConfigMapper.selectOneById(1L);
        log.info("biz a value {}", a);
        DynamicDataSourceContextHolder.clearDataSourceId();

        DynamicDataSourceContextHolder.setDataSourceId(DynamicDataSourceEnum.SHARD1);
        String b = kvConfigMapper.selectOneById(1L);
        log.info("biz b value {}", b);
        DynamicDataSourceContextHolder.clearDataSourceId();
    }

}
